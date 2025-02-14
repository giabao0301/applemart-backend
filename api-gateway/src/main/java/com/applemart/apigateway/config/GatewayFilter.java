package com.applemart.apigateway.config;

import com.applemart.apigateway.ApiResponse;
import com.applemart.apigateway.utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class GatewayFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;

    private String[] publicEndpoints = {
            "/api/v1/auth/registration",
            "/api/v1/auth/signup",
            "/api/v1/auth/registration/confirm",
            "/api/v1/auth/reset-password/confirm",
            "/api/v1/auth/login",
            "/oauth2/authorization/google",
            "/api/v1/auth/logout",
            "/api/v1/auth/refresh",
            "/api/v1/auth/introspect",
            "/api/v1/auth/reset-password",
    };

    private final Map<String, Set<String>> publicEndpointsMap = new HashMap<>() {{
        put("/api/v1/products", new HashSet<>(Set.of("GET")));
        put("/api/v1/products/.*", new HashSet<>(Set.of("GET")));
        put("/api/v1/productItems", new HashSet<>(Set.of("GET")));
        put("/api/v1/productItems/.*", new HashSet<>(Set.of("GET")));
        put("/api/v1/variationOptions", new HashSet<>(Set.of("GET")));
        put("/api/v1/categories", new HashSet<>(Set.of("GET")));
    }};


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Entering authentication filter...");

        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        String token = extractTokenFromCookie(exchange.getRequest());

        if (token == null) {
            log.info("No access token found in cookies");
            return unauthenticated(exchange.getResponse());
        }

        try {
            log.info("Token extracted from cookie: {}", token);
            jwtUtil.verifyToken(token);

            String userId = jwtUtil.extractSubject(token);
            List<String> roles = jwtUtil.extractScopes(token);

            log.info("User authenticated: userId={}, roles={}", userId, roles);

            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Roles", String.join(",", roles))
                    .build();

            return chain.filter(exchange.mutate()
                    .request(mutatedRequest)
                    .build());

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractTokenFromCookie(ServerHttpRequest request) {
        String cookieHeader = request.getHeaders().getFirst(HttpHeaders.COOKIE);

        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split("; ");
            for (String cookie : cookies) {
                if (cookie.startsWith("accessToken=")) {
                    return cookie.substring(("accessToken=").length());
                }
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        String method = request.getMethod().name();

        boolean isFullyPublic = Arrays.stream(publicEndpoints)
                .anyMatch(path::matches);

        if (isFullyPublic) {
            return true;
        }

        return publicEndpointsMap.entrySet().stream()
                .anyMatch(entry -> path.matches(entry.getKey()) &&
                        (entry.getValue() == null || entry.getValue().contains(method)));
    }


    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(403)
                .message("UNAUTHENTICATED")
                .build();

        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }
}
