package com.applemart.auth.registration;

import com.applemart.auth.user.UserDTO;

public interface RegistrationService {
    void register(RegistrationRequest registration);
    Integer confirmToken(String token);
}
