package com.applemart.order.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Retrieve the JWT token from SecurityContext
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getToken() != null) {
                Jwt jwt = (Jwt) authentication.getToken();
                String tokenValue = jwt.getTokenValue();
                // Attach the JWT token to the Authorization header
                template.header("Authorization", "Bearer " + tokenValue);
            }
        };
    }
}