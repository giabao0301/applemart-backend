package com.applestore.backend.productVariation;

import com.applestore.backend.productOption.ProductOptionDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationDTO {
    private Integer id;
    private String name;
    private List<ProductOptionDTO> options;
}
