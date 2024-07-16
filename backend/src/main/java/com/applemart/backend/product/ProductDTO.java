package com.applemart.backend.product;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private String imageUrl;
    private String slug;
}
