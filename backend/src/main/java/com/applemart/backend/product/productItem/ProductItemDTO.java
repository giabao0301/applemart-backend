package com.applemart.backend.product.productItem;

import com.applemart.backend.product.productConfiguration.ProductConfigurationDTO;
import com.applemart.backend.product.productImage.ProductImageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductItemDTO {
    private Integer id;
    private String productName;
    private String sku;

    @Min(value = 0, message = "Product quantity is invalid")
    private Integer quantity;

    @Min(value = 0, message = "Product price is invalid")
    private Double price;
    private String slug;

    private List<ProductImageDTO> images;
    private List<ProductConfigurationDTO> configurations;
}
