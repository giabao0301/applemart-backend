package com.applemart.productItem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class ProductConfigurationId implements Serializable {

    @Column(name = "product_item_id")
    private Integer productItemId;

    @Column(name = "variation_option_id")
    private Integer variationOptionId;
}
