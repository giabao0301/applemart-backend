package com.applemart.product.category;

import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String urlKey;
    private String imageUrl;
}
