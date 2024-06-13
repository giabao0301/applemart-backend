package com.applestore.backend.variation;

import com.applestore.backend.category.Category;
import com.applestore.backend.product.Product;
import com.applestore.backend.variationOption.ProductOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product_variation")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductOption> options;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void addProductOption(ProductOption productOption) {
        if(!options.contains(productOption)) {
            options.add(productOption);
            productOption.setVariation(this);
        }
    }
}
