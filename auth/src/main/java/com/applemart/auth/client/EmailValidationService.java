package com.applemart.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailValidationService {

    private final EmailValidationClient emailValidationClient;

    @Value("${abstractapi.api-key}")
    String apiKey = "your-api-key";

    public boolean validateEmail(String email) {
         EmailValidationResponse response = emailValidationClient.validateEmail(apiKey, email);

         Boolean isValidFormat = response.getIs_valid_format().getValue();
         Boolean isSmtpValid = response.getIs_smtp_valid().getValue();
         Boolean isDisposableEmail = response.getIs_disposable_email().getValue();
         if ( isValidFormat && isSmtpValid && !isDisposableEmail ) {
             return true;
         }

         return false;
    }
}
