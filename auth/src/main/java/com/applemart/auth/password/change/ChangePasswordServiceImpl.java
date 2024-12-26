package com.applemart.auth.password.change;

import com.applemart.auth.exception.RequestValidationException;
import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.user.User;
import com.applemart.auth.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {
    private final UserRepository userRepository;

    public ChangePasswordServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String id = authentication.getName();

        return userRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(id)));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = getLoggedInUser();

        String currentPassword = request.getCurrentPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(currentPassword, user.getPassword());

        if (!authenticated) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();

        if (newPassword.equals(currentPassword)) {
            throw new RequestValidationException("New password and current password must be different");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new BadCredentialsException("Confirm password does not match");
        }

        userRepository.updateUserPassword(user.getId(), passwordEncoder.encode(newPassword));
    }
}
