package com.applemart.auth;

import com.applemart.auth.response.ApiResponse;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Tag(
        name = "Auth",
        description = "REST APIs for Auth"
)
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) throws ParseException, JOSEException {
        AuthenticationResponse result = authenticationService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, result.getToken())
                .body("Login successful");
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(introspectResponse)
                .build();
    }
}
