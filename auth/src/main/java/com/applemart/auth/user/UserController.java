package com.applemart.auth.user;

import com.applemart.auth.common.ApiResponse;
import com.applemart.auth.common.PageResponse;
import com.applemart.auth.user.address.AddressDTO;
import com.applemart.auth.utils.AppConstants;
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
    public ResponseEntity<ApiResponse<PageResponse<UserDTO>>> getUsers(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @RequestParam(name = "dir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ) {
        PageResponse<UserDTO> users = userService.getUsers(page, size, sort, dir);

        ApiResponse<PageResponse<UserDTO>> apiResponse = ApiResponse.<PageResponse<UserDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(users)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable("id") Integer id) {
        ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getUserById(id))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationRequest request) {
        userService.createUser(request);

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable("id") Integer id, @RequestBody @Valid UserUpdateRequest request) {
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
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getAddress() {
        ApiResponse<List<AddressDTO>> apiResponse = ApiResponse.<List<AddressDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getAddress())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> getAddressById(@PathVariable("id") Integer id) {
        ApiResponse<AddressDTO> apiResponse = ApiResponse.<AddressDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.getAddressById(id))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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
    public ResponseEntity<ApiResponse<AddressDTO>> updateAddress(@PathVariable("id") Integer id, @RequestBody AddressDTO request) {
        ApiResponse<AddressDTO> apiResponse = ApiResponse.<AddressDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(userService.updateAddress(id, request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/addresses/{id}/default")
    public ResponseEntity<?> patchAddress(@PathVariable("id") Integer id) {
        userService.setDefaultAddress(id);
        return ResponseEntity.ok("Set default address successfully");
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Integer id) {
        userService.deleteAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
