package com.applemart.auth.registration;

import com.applemart.auth.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/resend-activation")
    public ResponseEntity<?> resendActivationEmail(@RequestBody ResendActivationRequest request) {
        registrationService.sendActivationEmail(request.getEmail());
        return ResponseEntity.ok("Activation email sent.");
    }


    @GetMapping(value = {"/registration/confirm", "/signup/confirm"})
    public ResponseEntity<String> confirm(@RequestParam("token") String token, HttpServletResponse response) {
        Integer userId = registrationService.confirmToken(token);
        String jwtToken = jwtUtil.issueToken(String.valueOf(userId), "USER");
        String refreshToken = jwtUtil.issueRefreshToken(String.valueOf(userId), "USER");

        Cookie accessTokenCookie = new Cookie("accessToken", jwtToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false); // true in production
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(10 * 60); // 10 minutes

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // true in production
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }
}
