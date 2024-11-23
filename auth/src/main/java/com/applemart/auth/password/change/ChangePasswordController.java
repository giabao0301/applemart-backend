package com.applemart.auth.password.change;

import com.applemart.auth.registration.RegistrationService;
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


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;

    @PutMapping("/change-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.changePassword(request);
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }
}
