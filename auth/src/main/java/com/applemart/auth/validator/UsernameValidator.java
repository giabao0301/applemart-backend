package com.applemart.auth.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9._-]{3,16}$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return false;
        }
        return username.matches(USERNAME_PATTERN);
    }
}
