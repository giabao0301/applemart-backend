package com.applemart.productItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProductConfigurationDTO {
    @JsonIgnore
    private ProductConfigurationId productConfigurationId;
    private VariationOptionDTO variationOption;
}
