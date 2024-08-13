package com.applemart.product.productConfiguration;

import com.applemart.product.variationOption.VariationOptionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProductConfigurationDTO {
    @JsonIgnore
    private ProductConfigurationId productConfigurationId;
    private VariationOptionDTO variationOption;
}
