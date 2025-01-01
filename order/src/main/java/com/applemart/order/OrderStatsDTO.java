package com.applemart.order;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderStatsDTO {
    private Double totalRevenue;
    private Long totalOrders;
}
