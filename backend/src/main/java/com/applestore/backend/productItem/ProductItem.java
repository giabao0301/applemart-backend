package com.applestore.backend.productItem;

import com.applestore.backend.product.Product;
import com.applestore.backend.productImage.ProductImage;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

    private String sku;

    @Column(name = "qty_in_stock")
    private Integer quantity;

    private Double price;

    private String slug;
}
