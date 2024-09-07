package com.applemart.apigateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        return authClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
