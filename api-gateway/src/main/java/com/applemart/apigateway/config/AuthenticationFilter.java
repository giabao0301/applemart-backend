package com.applemart.apigateway.config;

import com.applemart.apigateway.ApiResponse;
import com.applemart.apigateway.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    private String[] publicEndpoints = {
            "/api/v1/auth/.*",
            "/api/v1/users/.*",
            "/api/v1/products/.*",
            "/api/v1/products",
            "/api/v1/productItems/.*",
            "/api/v1/productItems",
            "/api/v1/categories",
            "/api/v1/categories/.*",
            "/api/v1/variationOptions",
            "/api/v1/carts",
            "/api/v1/carts/.*",
            "/api/v1/orders",
            "/api/v1/orders/.*",
            "/api/v1/payment/.*",
            "/actuator/**"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Entering authentication filter...");

        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        // Extract the token from the "accessToken" cookie
        String token = extractTokenFromCookie(exchange.getRequest());
        if (token == null) {
            log.info("No access token found in cookies");
            return unauthenticated(exchange.getResponse());
        }
        log.info("Token extracted from cookie: {}", token);

        // Introspect the token
        return authService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getData().getIsValid()) {
                return chain.filter(exchange);
            } else {
                return unauthenticated(exchange.getResponse());
            }
        }).onErrorResume(throwable -> {
            log.error("Error during token introspection", throwable);
            return unauthenticated(exchange.getResponse());
        });
    }

    private String extractTokenFromCookie(ServerHttpRequest request) {
        List<HttpCookie> cookies = request.getCookies().get("accessToken");
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).getValue();
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints)
                .anyMatch(s -> request.getURI().getPath().matches(s));
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(401)
                .message("UNAUTHENTICATED")
                .build();

        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }
}
