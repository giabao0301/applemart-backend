package com.applestore.backend.category;

import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String urlKey;
    private String imageUrl;
}
