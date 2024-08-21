package com.applemart.auth.user;

import com.applemart.auth.user.role.Role;
import com.applemart.auth.validator.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserCreationRequest {

    @Username
    private String username;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Email can't be null")
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;

    @Size(min = 10, max = 10, message = "Phone number is invalid")
    private String phoneNumber;

    private String profileImageUrl;

    private LocalDate dateOfBirth;

    @NotNull(message = "User role can't be null")
    private Set<Role> roles;
}
