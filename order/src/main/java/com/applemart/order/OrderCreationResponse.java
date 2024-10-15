package com.applemart.order;

import lombok.Data;

@Data
public class OrderCreationResponse {
    private String result;
    private String vnpayLink;
    private OrderDTO order;
}
