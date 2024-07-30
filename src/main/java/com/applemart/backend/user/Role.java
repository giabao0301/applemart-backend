package com.applemart.backend.user;

import com.applemart.backend.product.productItem.ProductItem;
import com.applemart.backend.product.variationOption.VariationOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();
}
