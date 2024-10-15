package com.applemart.order;

import lombok.Data;

@Data
public class OrderLineDTO {
    private Integer id;
    private Integer productItemId;
    private Integer quantity;
}
