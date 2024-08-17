package com.applemart.auth.client;

import lombok.Data;

@Data
public class EmailValidationResponse {
    private String email;
    private String autocorrect;
    private String deliverability;
    private double quality_score;
    private EmailAttribute is_valid_format;
    private EmailAttribute is_free_email;
    private EmailAttribute is_disposable_email;
    private EmailAttribute is_role_email;
    private EmailAttribute is_catchall_email;
    private EmailAttribute is_mx_found;
    private EmailAttribute is_smtp_valid;
}

@Data
class EmailAttribute {
    private Boolean value;
    private String text;
}
