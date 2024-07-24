package com.applemart.backend.user;

import com.applemart.backend.common.response.ApiResponse;
import com.applemart.backend.user.address.AddressDTO;
import com.applemart.backend.utils.JWTUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api-prefix.path}")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsers() {
        ApiResponse<List<UserDTO>> apiResponse = ApiResponse.<List<UserDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getUsers())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value = {"/users", "/auth/register", "/auth/signup"})
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest request) {
        userService.createUser(request);

        String jwtToken = jwtUtil.issueToken(request.getUsername(),"USER");

         return ResponseEntity.status(HttpStatus.CREATED)
                 .header(HttpHeaders.AUTHORIZATION, jwtToken)
                 .body("User registered successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
         userService.deleteUserById(id);

         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@RequestBody @Valid UserUpdateRequest request) {
         userService.updateUser(request);
        return null;
    }

    @GetMapping("/users/profile")
    public ResponseEntity<ApiResponse<UserDTO>> getUserProfile() {
        ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getUserProfile())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddress() {
        return new ResponseEntity<>(userService.getAddress(), HttpStatus.OK);
    }

    @PostMapping("/users/addresses")
    public ResponseEntity<ApiResponse<AddressDTO>> addAddress(@RequestBody AddressDTO request) {
        ApiResponse<AddressDTO> apiResponse = ApiResponse.<AddressDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("OK")
                .data(userService.addAddress(request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
