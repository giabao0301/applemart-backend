package com.applemart.backend.product.productImage;

import com.applemart.backend.product.productItem.ProductItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

}