package com.applemart.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCancellationRequest {
    private Integer userId;
    private Integer orderId;
}
