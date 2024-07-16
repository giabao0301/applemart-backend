package com.applemart.backend.product.variation;

import com.applemart.backend.product.variationOption.VariationOptionDTO;
import lombok.Data;

import java.util.List;

@Data
public class VariationDTO {
    private Integer id;
    private String name;
    private List<VariationOptionDTO> options;
}
