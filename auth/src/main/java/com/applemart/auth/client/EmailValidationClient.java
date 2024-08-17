package com.applemart.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "emailValidationClient", url = "https://emailvalidation.abstractapi.com/v1")
public interface EmailValidationClient {

    @GetMapping("/")
    EmailValidationResponse validateEmail(@RequestParam("api_key") String apiKey,
                                          @RequestParam("email") String email);
}
