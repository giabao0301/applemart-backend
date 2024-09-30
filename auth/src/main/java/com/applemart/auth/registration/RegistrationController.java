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

        UserDTO user = registrationService.register(request);

        String jwtToken = jwtUtil.issueToken(String.valueOf(user.getId()), "USER");

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body("User registered successfully");
    }

    @GetMapping(value = {"/registration/confirm", "/signup/confirm"})
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationService.confirmToken(token), HttpStatus.OK);
    }
}
