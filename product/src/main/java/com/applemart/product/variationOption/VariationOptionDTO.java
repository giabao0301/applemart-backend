package com.applemart.product.variationOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariationOptionDTO {
    private Integer id;
    private String value;
    private String name;
}
