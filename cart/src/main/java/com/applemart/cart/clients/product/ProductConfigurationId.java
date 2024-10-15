package com.applemart.cart.clients.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductConfigurationId  {
    private Integer productItemId;
    private Integer variationOptionId;
}
