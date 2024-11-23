package com.applemart.auth.registration;

import com.applemart.auth.validator.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

    @Username
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Full name is required")
    @Size(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;
}
