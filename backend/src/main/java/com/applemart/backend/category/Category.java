package com.applemart.backend.category;
import com.applemart.backend.product.Product;
import com.applemart.backend.product.variation.Variation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String urlKey;

    private String imageUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Variation> variations = new ArrayList<>();

    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
            product.setCategory(this);
        }
    }
}
