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
public class ProductItemDTO {
    private Integer id;
    private String productName;
    private String sku;
    private Integer quantity;
    private Double price;
    private String imageUrl;
    private String slug;
    private List<ProductAttributeDTO> attributes;
    private List<ProductConfigurationDTO> configurations;
}
