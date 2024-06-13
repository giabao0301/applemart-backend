package com.applestore.backend.productImage;

import com.applestore.backend.productItem.ProductItem;
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

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    private String imageUrl;

}