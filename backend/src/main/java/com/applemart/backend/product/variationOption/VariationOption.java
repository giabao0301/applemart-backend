package com.applemart.backend.product.variationOption;

import com.applemart.backend.product.productConfiguration.ProductConfiguration;
import com.applemart.backend.product.variation.Variation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "variation_option")
public class VariationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String value;

    @ManyToOne()
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;

    @OneToMany(mappedBy = "variationOption")
    List<ProductConfiguration> configurations = new ArrayList<>();
}
