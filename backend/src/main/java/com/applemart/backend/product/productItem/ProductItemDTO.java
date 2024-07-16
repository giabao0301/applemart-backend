package com.applemart.backend.product.productItem;

import com.applemart.backend.product.productConfiguration.ProductConfigurationDTO;
import com.applemart.backend.product.productImage.ProductImageDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductItemDTO {
    private Integer id;
    private String productName;
    private String sku;
    private Integer quantity;
    private Double price;
    private String slug;
    private List<ProductImageDTO> images;
    private List<ProductConfigurationDTO> configurations;
}
