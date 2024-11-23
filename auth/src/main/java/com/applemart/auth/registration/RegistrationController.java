package com.applemart.auth.registration;

import com.applemart.auth.user.UserDTO;
import com.applemart.auth.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Auth",
        description = "REST APIs for Auth"
)
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;

    @Operation(
            summary = "Register API",
            description = "Register a new user, can be used with both '/register' and '/signup' endpoints"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) {

        registrationService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully, please verify OTP sent to your email address to activate your account");
    }

    @GetMapping(value = {"/registration/confirm", "/signup/confirm"})
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        Integer userId = registrationService.confirmToken(token);
        String jwtToken = jwtUtil.issueToken(String.valueOf(userId), "USER");

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body("User registered successfully");
    }
}
