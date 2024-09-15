package com.applemart.product.variationOption;

import java.util.List;

public interface VariationOptionService {
    List<VariationOptionDTO> getVariationOptionsByProductId(Integer id);
}
