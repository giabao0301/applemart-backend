package com.applemart.product.productItem;

import com.applemart.product.productAttribute.ProductAttributeDTO;
import com.applemart.product.productConfiguration.ProductConfigurationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductItemDTO {
    private Integer id;
    private String productName;
    private String name;

    @Min(value = 0, message = "Product quantity is invalid")
    private Integer quantityInStock;

    @Min(value = 0, message = "Product price is invalid")
    private Double price;
    private String imageUrl;
    private String slug;

    private List<ProductConfigurationDTO> configurations;

    private List<ProductAttributeDTO> attributes;
}
