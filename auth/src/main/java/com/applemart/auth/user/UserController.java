package com.applemart.auth.user;

import com.applemart.auth.ApiResponse;
import com.applemart.auth.registration.RegistrationRequest;
import com.applemart.auth.user.address.AddressDTO;
import com.applemart.auth.utils.JWTUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "User",
        description = "REST APIs for User"
)
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsers() {
        ApiResponse<List<UserDTO>> apiResponse = ApiResponse.<List<UserDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getUsers())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable Integer id) {
        ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getUserById(id))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value ="")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationRequest request) {
        userService.createUser(request);

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Integer id, @RequestBody @Valid UserUpdateRequest request) {
        ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.updateUser(id, request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> getUserProfile() {
        ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getUserProfile())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddress() {
        return new ResponseEntity<>(userService.getAddress(), HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<AddressDTO>> addAddress(@RequestBody AddressDTO request) {
        ApiResponse<AddressDTO> apiResponse = ApiResponse.<AddressDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("OK")
                .data(userService.addAddress(request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> updateAddress(@PathVariable Integer id, @RequestBody AddressDTO request) {
        ApiResponse<AddressDTO> apiResponse = ApiResponse.<AddressDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.updateAddress(id, request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {
        userService.deleteAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
