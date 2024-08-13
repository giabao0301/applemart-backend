package com.applemart.auth.utils;

import java.security.SecureRandom;

public class OTPGenerator {

    // Method to generate OTP of a specified length
    public static String generateOTP(int length) {
        // Characters allowed in the OTP
        String numbers = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);

        // Generate OTP by selecting random characters from the allowed characters
        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return otp.toString();
    }
}

