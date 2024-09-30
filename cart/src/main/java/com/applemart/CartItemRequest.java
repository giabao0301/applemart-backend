package com.applemart;

import lombok.Data;

import java.util.Map;

@Data
public class CartItemRequest {
    private Integer userId;
    private Map<String, Integer> items;
}
