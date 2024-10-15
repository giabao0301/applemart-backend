package com.applemart.order;

import com.applemart.order.payment.PaymentMethod;
import com.applemart.order.shipping.ShippingMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id;
    private Integer userId;
    private LocalDateTime orderDate;
    private Integer addressId;
    private Double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String shippingMethod;
    private String orderStatus;
    private List<OrderLineDTO> orderLines = new ArrayList<>();
}
