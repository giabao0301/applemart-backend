package com.applestore.backend.product;

import com.applestore.backend.category.Category;
import com.applestore.backend.productItem.ProductItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    private String slug;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductItem> productItems = new HashSet<>();


    public void addProductItem(ProductItem productItem) {
        if (!productItems.contains(productItem)) {
            productItems.add(productItem);
            productItem.setProduct(this);
        }
    }

}
