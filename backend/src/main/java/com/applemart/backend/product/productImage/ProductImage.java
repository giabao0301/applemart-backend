package com.applemart.backend.product.productImage;

import com.applemart.backend.product.productItem.ProductItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageUrl;

    @ManyToOne()
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;


}