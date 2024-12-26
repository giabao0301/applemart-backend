package com.applemart.auth.registration;

import com.applemart.auth.exception.DuplicateResourceException;
import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.token.Token;
import com.applemart.auth.token.TokenRepository;
import com.applemart.auth.user.*;
import com.applemart.auth.clients.EmailValidationService;
import com.applemart.auth.user.role.Role;
import com.applemart.auth.user.role.RoleRepository;
import com.applemart.auth.utils.OTPGenerator;
import com.applemart.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final TokenRepository tokenRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EmailValidationService emailValidationService;

    @Override
    public void register(RegistrationRequest request) {

//        if (emailValidationService.validateEmail(request.getEmail())) {
//            throw new ResourceNotFoundException("Email doesn't exist");
//        }

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

        user.setEnabled(false);

        userDTOMapper.toDTO(userRepository.save(user));

        this.sendActivationEmail(user.getEmail());
    }

    @Override
    public Integer confirmToken(String token) {
        Token confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        Integer id = confirmationToken.getUser().getId();
        userRepository.enableUser(id);

        tokenRepository.delete(confirmationToken);

        return id;
    }

    @Override
    public void sendActivationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [%s] not found".formatted(email)));

        String token = OTPGenerator.generateOTP(6);
        Token confirmationToken = Token.builder()
                .token(token)
                .type("confirmation")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .user(user)
                .build();


        tokenRepository.save(confirmationToken);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .subject("%s là mã xác nhận email của bạn".formatted(token))
                .message(token)
                .toUserEmail(user.getEmail())
                .toUserName(user.getFullName())
                .build();

        kafkaTemplate.send("notification", notificationRequest);
    }
}
