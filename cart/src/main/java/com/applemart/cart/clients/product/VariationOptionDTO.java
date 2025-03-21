package com.applemart.cart.clients.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariationOptionDTO {
    private Integer id;
    private String name;
    private String value;
    private String imageUrl;
}
