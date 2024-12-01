package com.applemart.auth.password.reset;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/reset-password")
    public ResponseEntity<?> requestPasswordReset(@RequestBody @Valid PasswordResetRequest request) {
        passwordResetService.requestPasswordReset(request);
        return new ResponseEntity<>("Password reset requested successfully", HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordUpdateRequest request, @RequestParam("token") String token) {
        passwordResetService.resetPassword(request, token);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

    @GetMapping(value = {"/reset-password/confirm"})
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return new ResponseEntity<>(passwordResetService.confirmToken(token), HttpStatus.OK);
    }
}
