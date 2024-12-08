package com.applemart.product.category;
import com.applemart.product.Product;
import com.applemart.product.variation.Variation;
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

    private String thumbnailUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childrenCategory = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Variation> variations = new ArrayList<>();

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void addVariation(Variation variation) {
        variations.add(variation);
        variation.setCategory(this);
    }

    public boolean removeVariation(Variation variation) {
        return variations.remove(variation);
    }
}
