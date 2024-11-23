package com.applemart.auth.password.reset;

public interface PasswordResetService {
    void requestPasswordReset(PasswordResetRequest request);
    void resetPassword(PasswordUpdateRequest request);
    String confirmToken(String token);
}
