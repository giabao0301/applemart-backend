package com.applemart.auth.registration;

import com.applemart.auth.exception.DuplicateResourceException;
import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.registration.token.ConfirmationToken;
import com.applemart.auth.registration.token.ConfirmationTokenRepository;
import com.applemart.auth.user.*;
import com.applemart.auth.client.EmailValidationService;
import com.applemart.auth.utils.OTPGenerator;
import com.applemart.clients.notification.NotificationClient;
import com.applemart.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final NotificationClient notificationClient;
    private final EmailValidationService emailValidationService;

    @Override
    public void register(RegistrationRequest request) {

        if (!emailValidationService.validateEmail(request.getEmail())) {
            throw new ResourceNotFoundException("Email doesn't exist");
        }

        User user = userDTOMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email address already in use");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (!roleRepository.existsByName(role.getName())) {
                throw new ResourceNotFoundException("Role [%s] not found".formatted(role.getName()));
            }
        }

        userRepository.save(user);

        String token = OTPGenerator.generateOTP(6);
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenRepository.save(confirmationToken);


        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message(token)
                .toUserEmail(user.getEmail())
                .toUserName(user.getFullName())
                .build();

        notificationClient.publishNotification(notificationRequest);
    }

    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Confirmation token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new DuplicateResourceException("Confirmation token already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("Token expired");
        }

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        Integer id = confirmationToken.getUser().getId();
        userRepository.enableUser(id);

        return "Token confirmed";
    }
}
