package com.applestore.backend.variation;

import com.applestore.backend.variationOption.variationOptionDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationDTO {
    private Integer id;
    private String name;
    private List<variationOptionDTO> options;
}
