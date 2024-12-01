package com.applemart.order.shipping;

import com.applemart.order.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/shippingMethods")
@RequiredArgsConstructor
public class ShippingMethodController {
    private final ShippingMethodRepository shippingMethodRepository;
    private final ShippingMethodDTOMapper shippingMethodDTOMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShippingMethodDTO>>> getShippingMethods() {
        List<ShippingMethodDTO> shippingMethods = shippingMethodRepository.findAll().stream().map(shippingMethodDTOMapper::toDTO).toList();
        ApiResponse<List<ShippingMethodDTO>> apiResponse = ApiResponse.<List<ShippingMethodDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(shippingMethods)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
