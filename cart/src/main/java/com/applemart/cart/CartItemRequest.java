package com.applemart.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class CartItemRequest {
    @NotBlank(message = "Product Item Id is required")
    private Integer productItemId;


    @NotBlank(message = "Quantity is required")
    @Min(value = 0, message = "Product quantity is invalid")
    private Integer quantity;
}
