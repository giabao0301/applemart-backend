package com.applemart.product.category;

import com.applemart.product.variation.VariationDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private String urlKey;
    private String thumbnailUrl;
}
