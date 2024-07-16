package com.applemart.backend.product.variation;

import com.applemart.backend.category.Category;
import com.applemart.backend.product.variationOption.VariationOption;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VariationOption> options;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void addProductOption(VariationOption variationOption) {
        if(!options.contains(variationOption)) {
            options.add(variationOption);
            variationOption.setVariation(this);
        }
    }
}
