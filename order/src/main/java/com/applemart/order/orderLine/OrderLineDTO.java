package com.applemart.order.orderLine;

import lombok.Data;

@Data
public class OrderLineDTO {
    private Integer id;
    private Integer productItemId;
    private Integer quantity;
}
