package com.applemart.auth.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "Username must start with a letter and be between 3 and 16 characters long, and can only contain letters, numbers, underscores (_), hyphens (-), and periods (.)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
