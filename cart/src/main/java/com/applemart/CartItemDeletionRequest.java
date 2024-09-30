package com.applemart;

import lombok.Data;

import java.util.List;

@Data
public class CartItemDeletionRequest {
    private Integer userId;
    private List<String> productItemIds;
}
