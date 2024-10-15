package com.applemart.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartItemDeletionRequest {
    private List<String> productItemIds;
}
