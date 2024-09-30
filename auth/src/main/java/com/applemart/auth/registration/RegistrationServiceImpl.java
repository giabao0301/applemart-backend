package com.applemart.auth.registration;

import com.applemart.auth.exception.DuplicateResourceException;
import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.registration.token.ConfirmationToken;
import com.applemart.auth.registration.token.ConfirmationTokenRepository;
import com.applemart.auth.user.*;
import com.applemart.auth.clients.EmailValidationService;
import com.applemart.auth.user.role.Role;
import com.applemart.auth.user.role.RoleRepository;
import com.applemart.auth.utils.OTPGenerator;
import com.applemart.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EmailValidationService emailValidationService;

    @Override
    public UserDTO register(RegistrationRequest request) {

        if (emailValidationService.validateEmail(request.getEmail())) {
            throw new ResourceNotFoundException("Email doesn't exist");
        }

        User user = userDTOMapper.toEntity(request);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email address already in use");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findByName("USER")
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roles.add(role);
        user.setRoles(roles);
        UserDTO userDTO = userDTOMapper.toDTO(userRepository.save(user));

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

        kafkaTemplate.send("notification", notificationRequest);

        return userDTO;
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
