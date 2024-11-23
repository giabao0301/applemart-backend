package com.applemart.cart.clients.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Integer id;
    private Integer quantity;
    private ProductItemDTO productItem;
}
