package com.applestore.backend.productItem;

import com.applestore.backend.productImage.ProductImage;
import lombok.Data;

import java.util.List;

@Data
public class ProductItemDTO {
    private Integer id;
    private String productName;
    private List<ProductImage> images;
    private String sku;
    private Integer quantity;
    private Double price;
    private String slug;
}
