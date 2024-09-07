package com.applemart.product.variation;

import com.applemart.product.Product;
import com.applemart.product.category.Category;
import com.applemart.product.variationOption.VariationOption;
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
@Table(name = "variation")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<VariationOption> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
