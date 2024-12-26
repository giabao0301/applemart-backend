package com.applemart.apigateway.config;


import com.applemart.apigateway.exception.AccessDeniedExceptionHandler;
import com.applemart.apigateway.exception.AuthenticationExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000")); // Replace with your frontend URL
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.addExposedHeader("Authorization");

        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public AuthenticationExceptionHandler authenticationExceptionHandler() {
        return new AuthenticationExceptionHandler(objectMapper);
    }

    @Bean
    public AccessDeniedExceptionHandler accessDeniedExceptionHandler() {
        return new AccessDeniedExceptionHandler(objectMapper);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(auth -> auth
//                        .pathMatchers("/actuator/**").permitAll()
//                        .pathMatchers(HttpMethod.POST, "api/v1/auth/signup").permitAll()
//                        .pathMatchers(HttpMethod.POST, "api/v1/auth/register").permitAll()
//                        .pathMatchers(HttpMethod.POST, "api/v1/auth/login").permitAll()
//                        .pathMatchers(HttpMethod.GET, "api/v1/auth/logout").permitAll()
//                        .pathMatchers(HttpMethod.GET, "api/v1/users/profile").permitAll()
//                        .pathMatchers(HttpMethod.GET, "api/v1/products/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "api/v1/productItems/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "api/v1/categories/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "api/v1/notifications/**").permitAll()
//
//                        .pathMatchers(HttpMethod.GET, "api/v1/users").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.POST, "api/v1/users/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.PUT, "api/v1/users/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.DELETE, "api/v1/users/**").hasAuthority("ROLE_ADMIN")
//
//                        .pathMatchers(HttpMethod.POST, "api/v1/products/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.PUT, "api/v1/products/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.DELETE, "api/v1/products/**").hasAuthority("ROLE_ADMIN")
//
//                        .pathMatchers(HttpMethod.POST, "api/v1/categories/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.PUT, "api/v1/categories/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.DELETE, "api/v1/categories/**").hasAuthority("ROLE_ADMIN")
//
//                        .pathMatchers(HttpMethod.POST, "api/v1/notifications/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.PUT, "api/v1/notifications/**").hasAuthority("ROLE_ADMIN")
//                        .pathMatchers(HttpMethod.DELETE, "api/v1/notifications/**").hasAuthority("ROLE_ADMIN")
//
//                        .anyExchange().authenticated()
//                )
                .build();
    }
}
