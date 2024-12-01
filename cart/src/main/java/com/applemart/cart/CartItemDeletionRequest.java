package com.applemart.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CartItemDeletionRequest {
    @NotBlank(message = "Product Item id is required")
    private List<String> productItemIds;
}
