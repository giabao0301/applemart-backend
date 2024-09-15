package com.applemart.product;

import com.applemart.product.productImage.ProductImageDTO;
import com.applemart.product.productItem.ProductItem;
import com.applemart.product.productItem.ProductItemDTO;
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
    private List<ProductImageDTO> images;
}
