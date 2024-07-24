package com.applemart.backend.product.productConfiguration;

import com.applemart.backend.product.productItem.ProductItemDTO;
import com.applemart.backend.product.variationOption.VariationOptionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProductConfigurationDTO {
    @JsonIgnore
    private ProductConfigurationId productConfigurationId;
    private VariationOptionDTO variationOption;
}
