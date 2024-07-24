package com.applemart.backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    @NotBlank(message = "Username can't be blank")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @Email
    private String email;

    private String fullName;

    private LocalDate dateOfBirth;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Phone number can't be blank")
    @Size(min = 10, max = 10, message = "Phone number is invalid")
    private String phoneNumber;

    private String profileImageUrl;
}
