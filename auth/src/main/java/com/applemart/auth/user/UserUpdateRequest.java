package com.applemart.auth.user;

import com.applemart.auth.validator.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    @Username
    private String username;

    @Email(message = "Invalid email")
    private String email;

    private String fullName;

    private LocalDate dateOfBirth;

    @Size(min = 10, max = 10, message = "Phone number is invalid")
    private String phoneNumber;

    private String profileImageUrl;
}
