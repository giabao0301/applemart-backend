package com.applemart.auth.registration;

public interface RegistrationService {
    void register(RegistrationRequest registration);
    Integer confirmToken(String token);
    void sendActivationEmail(String email);
}
