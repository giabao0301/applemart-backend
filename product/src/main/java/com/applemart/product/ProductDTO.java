package com.applemart.product;

import com.applemart.product.productImage.ProductImageDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String category;
    private String parentCategory;
    private Double lowestPrice;
    private String description;
    private String thumbnailUrl;
    private String slug;
    private Integer releaseYear;
    private List<ProductImageDTO> images;
}
