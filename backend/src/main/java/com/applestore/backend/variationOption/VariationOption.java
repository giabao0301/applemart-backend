package com.applestore.backend.productOption;

import com.applestore.backend.productVariation.ProductVariation;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product_option")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String value;

    @ManyToOne()
    @JoinColumn(name = "variation_id", nullable = false)
    private ProductVariation variation;
}
