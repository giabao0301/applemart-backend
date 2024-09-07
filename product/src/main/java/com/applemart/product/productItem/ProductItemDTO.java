package com.applemart.product.productItem;

import com.applemart.product.productAttribute.ProductAttributeDTO;
import com.applemart.product.productConfiguration.ProductConfigurationDTO;
import com.applemart.product.productImage.ProductImageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private String imageUrl;
    private String slug;

    private List<ProductAttributeDTO> attributes;

    @NotNull(message = "Product item's configuration can't be null")
    private List<ProductConfigurationDTO> configurations;
}
