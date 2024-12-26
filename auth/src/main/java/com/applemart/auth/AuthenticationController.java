package com.applemart.auth;

import com.applemart.auth.common.ApiResponse;
import com.applemart.auth.utils.JWTUtil;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;

@Tag(
        name = "Auth",
        description = "REST APIs for Auth"
)
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) throws ParseException, JOSEException {
        AuthenticationResponse result = authenticationService.login(request);

        String userId = jwtUtil.extractSubject(result.getToken());

        String refreshToken = jwtUtil.issueRefreshToken(userId, "USER");

        Cookie accessTokenCookie = new Cookie("accessToken", result.getToken());
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

        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request, HttpServletRequest httpServletRequest) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(introspectResponse)
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) throws ParseException, JOSEException {
        // Extract the refresh token from cookies
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        jwtUtil.verifyToken(refreshToken);

        String userId = jwtUtil.extractSubject(refreshToken);

        String newAccessToken = jwtUtil.issueToken(userId);

        Cookie newAccessTokenCookie = new Cookie("accessToken", newAccessToken);
        newAccessTokenCookie.setHttpOnly(true);
        newAccessTokenCookie.setSecure(false); // true in production
        newAccessTokenCookie.setPath("/");
        newAccessTokenCookie.setMaxAge(10 * 60); // 10 minutes
        response.addCookie(newAccessTokenCookie);

        return ResponseEntity.ok("Access token refreshed");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false); // true in production
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // true in production
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}
