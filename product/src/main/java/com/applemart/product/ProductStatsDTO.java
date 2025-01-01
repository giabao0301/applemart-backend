package com.applemart.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductStatsDTO {
    private Long totalProductItems;
    private Long totalCategories;
}
