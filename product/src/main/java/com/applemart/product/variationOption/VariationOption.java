package com.applemart.product.variationOption;

import com.applemart.product.productConfiguration.ProductConfiguration;
import com.applemart.product.variation.Variation;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "`value`", nullable = false)
    private String value;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;

    @OneToMany(mappedBy = "variationOption")
    private List<ProductConfiguration> configurations = new ArrayList<>();
}
