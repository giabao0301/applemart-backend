package com.applemart.auth.registration;

import com.applemart.auth.utils.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) {

        registrationService.register(request);

        String jwtToken = jwtUtil.issueToken(request.getUsername(), "USER");

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body("User registered successfully");
    }

    @GetMapping(value = {"/registration/confirm", "/signup/confirm"})
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return new ResponseEntity<>(registrationService.confirmToken(token), HttpStatus.OK);
    }
}
