package com.applemart.order;

import com.applemart.order.orderLine.OrderLineDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderUpdateRequestForUser {
    private Integer id;
    private Integer userId;
    private Integer addressId;
    private String paymentMethod;
    private String shippingMethod;
    private List<OrderLineDTO> orderLines = new ArrayList<>();
}
