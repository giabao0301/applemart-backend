package com.applestore.backend.productVariation;

import com.applestore.backend.product.Product;
import com.applestore.backend.productOption.ProductOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product_variation")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String name;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductOption> options;
}
