package com.applemart.product.productItem;

import com.applemart.product.Product;
import com.applemart.product.productAttribute.ProductAttribute;
import com.applemart.product.productConfiguration.ProductConfiguration;
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
@Table(name = "product_item")
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String imageUrl;

    private String name;

    @Column(name = "qty_in_stock")
    private Integer quantity;

    private Double price;

    private String slug;

    @OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductConfiguration> configurations = new ArrayList<>();

    @OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttribute> attributes = new ArrayList<>();
}
