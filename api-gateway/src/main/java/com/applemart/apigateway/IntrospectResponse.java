package com.applemart.apigateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectResponse {
    private Boolean isValid;
    private String userId;
    private List<String> authorities;
}
