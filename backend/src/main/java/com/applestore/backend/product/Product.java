package com.applestore.backend.product;

import com.applestore.backend.category.Category;
import com.applestore.backend.productItem.ProductItem;
import com.applestore.backend.productVariation.ProductVariation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    private String slug;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductItem> productItems = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariation> variations = new ArrayList<>();

    public void addProductItem(ProductItem productItem) {
        if (!productItems.contains(productItem)) {
            productItems.add(productItem);
            productItem.setProduct(this);
        }
    }
}
