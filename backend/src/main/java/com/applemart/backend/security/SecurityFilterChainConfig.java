package com.applemart.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/users",
                                        "/api/v1/users/*/addresses",
                                        "/api/v1/auth/**")
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/products",
                                        "/api/v1/categories")
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/users"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/products",
                                        "/api/v1/categories")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwtConfigurer ->
                                        jwtConfigurer
                                                .decoder(jwtDecoder)
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter))

                                .authenticationEntryPoint(authenticationEntryPoint)
                );

        return http.build();
    }


}