package com.applemart.auth.registration;

public interface RegistrationService {
    void register(RegistrationRequest registration);
    String confirmToken(String token);
}
