package com.applemart.order.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final CustomJwtDecoder customJwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/actuator/**")
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/payments/**",
                                        "/api/v1/shippingMethods/**",
                                        "/api/v1/orders/search/**"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/orders",
                                        "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/orders",
                                        "/api/v1/orders/**"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/orders/info"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/orders/**"
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/orders"
                                )
                                .permitAll()
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/orders/**"
                                )
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwtConfigurer ->
                                        jwtConfigurer
                                                .decoder(customJwtDecoder)
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter))

                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }


}