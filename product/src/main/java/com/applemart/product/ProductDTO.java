package com.applemart.product;

import com.applemart.product.productAttribute.ProductAttributeDTO;
import com.applemart.product.productImage.ProductImageDTO;
import com.applemart.product.variation.VariationDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String category;
    private Double lowestPrice;
    private String description;
    private String thumbnailUrl;
    private String slug;
    private List<ProductImageDTO> images;
    private List<VariationDTO> variations;
}
