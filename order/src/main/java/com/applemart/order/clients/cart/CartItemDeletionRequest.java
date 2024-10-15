package com.applemart.order.clients.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartItemDeletionRequest {
    private List<Integer> productItemIds;
}
