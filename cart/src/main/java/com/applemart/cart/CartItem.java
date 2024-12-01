package com.applemart.cart;

import com.applemart.cart.clients.product.ProductItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Integer id;
    private Integer quantity;
    private ProductItemDTO productItem;
}
