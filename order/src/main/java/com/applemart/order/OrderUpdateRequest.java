package com.applemart.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderUpdateRequest {
    private Integer id;
    private Integer userId;
    private LocalDateTime orderDate;
    private Integer addressId;
    private String paymentMethod;
    private String paymentStatus;
    private String shippingMethod;
    private String orderStatus;
    private List<OrderLineDTO> orderLines = new ArrayList<>();
}
