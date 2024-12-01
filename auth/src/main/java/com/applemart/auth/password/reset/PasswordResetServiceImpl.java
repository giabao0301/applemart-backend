package com.applemart.auth.password.reset;

import com.applemart.auth.exception.RequestValidationException;
import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.registration.RegistrationService;
import com.applemart.auth.token.Token;
import com.applemart.auth.token.TokenRepository;
import com.applemart.auth.user.User;
import com.applemart.auth.user.UserRepository;
import com.applemart.auth.user.UserService;
import com.applemart.auth.utils.OTPGenerator;
import com.applemart.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RegistrationService registrationService;

    @Override
    public void requestPasswordReset(PasswordResetRequest request) {
        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [%s] not found".formatted(email)));

        String token = OTPGenerator.generateOTP(6);
        Token resetToken = Token.builder()
                .token(token)
                .type("password-reset")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();

        tokenRepository.save(resetToken);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .subject("%s là mã khôi phục mật khẩu của bạn".formatted(token))
                .toUserEmail(user.getEmail())
                .toUserName(user.getFullName())
                .message(token)
                .build();

        kafkaTemplate.send("notification", notificationRequest);
    }

    @Override
    public void resetPassword(PasswordUpdateRequest request, String token) {

        Token passwordResetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if (!passwordResetToken.getUser().getEmail().equals(request.getEmail())) {
            throw new BadCredentialsException("Invalid email");
        }

        String email = request.getEmail();
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [%s] not found".formatted(email)));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RequestValidationException("New password and current password must be different");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new BadCredentialsException("Confirm password does not match");
        }

        userRepository.updateUserPassword(user.getId(), passwordEncoder.encode(newPassword));
        tokenRepository.delete(passwordResetToken);
    }

    @Override
    public String confirmToken(String token) {
        Token passwordResetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        return "http://localhost:3000/reset-password?token=%s&email=%s".formatted(passwordResetToken.getToken(), passwordResetToken.getUser().getEmail());
    }
}
