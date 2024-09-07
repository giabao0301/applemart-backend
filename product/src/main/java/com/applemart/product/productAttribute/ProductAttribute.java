package com.applemart.product.productAttribute;

import com.applemart.product.Product;
import com.applemart.product.productItem.ProductItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_attribute")
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`key`", unique = true, nullable = false)
    private String key;

    @Column(name = "`value`", nullable = false)
    private String value;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;
}
