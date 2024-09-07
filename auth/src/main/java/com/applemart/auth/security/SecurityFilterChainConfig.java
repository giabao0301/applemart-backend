package com.applemart.auth.security;

import com.applemart.auth.user.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final CustomJwtDecoder customJwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

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
                                        "/api/v1/auth/registration/confirm",
                                        "/api/v1/auth/signup/confirm",
                                        "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/users"
                                )
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())

                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwtConfigurer ->
                                        jwtConfigurer
                                                .decoder(customJwtDecoder)
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter))

                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler))
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(customOAuth2UserService)
                                )
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/") // Redirect to home page after logout
                                .invalidateHttpSession(true) // Invalidate session
                                .clearAuthentication(true) // Clear authentication
                                .deleteCookies("JSESSIONID") // Delete cookies
                                .permitAll()
                );

        return http.build();
    }


}