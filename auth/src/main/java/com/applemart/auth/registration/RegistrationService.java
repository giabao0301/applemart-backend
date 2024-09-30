package com.applemart.auth.registration;

import com.applemart.auth.user.UserDTO;

public interface RegistrationService {
    UserDTO register(RegistrationRequest registration);
    String confirmToken(String token);
}
