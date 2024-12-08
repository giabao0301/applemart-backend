package com.applemart.product.variation;

import com.applemart.product.Product;
import com.applemart.product.category.Category;
import com.applemart.product.variationOption.VariationOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariationOption> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void addVariationOption(VariationOption option) {
        options.add(option);
        option.setVariation(this);
    }

    public boolean removeVariationOption(VariationOption option) {
        return options.remove(option);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variation variation = (Variation) o;
        return Objects.equals(id, variation.id) && Objects.equals(name, variation.name) && Objects.equals(options, variation.options) && Objects.equals(category, variation.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, options, category);
    }
}
