package com.applemart.order.clients.product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductItemDTO {
    private Integer id;
    private String productName;
    private String name;

    @Min(value = 0, message = "Product quantity is invalid")
    private Integer quantity;

    @Min(value = 0, message = "Product price is invalid")
    private Double price;

    private String imageUrl;

    private String slug;

    private List<ProductAttributeDTO> attributes;

    private List<ProductConfigurationDTO> configurations;
}
