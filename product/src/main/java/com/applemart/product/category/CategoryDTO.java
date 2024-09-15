package com.applemart.product.category;

import com.applemart.product.variation.VariationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    private String name;
    private String parentCategory;
    private String urlKey;
    private String thumbnailUrl;
    private List<VariationDTO> variations;
}
