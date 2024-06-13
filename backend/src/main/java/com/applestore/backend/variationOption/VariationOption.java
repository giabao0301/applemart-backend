package com.applestore.backend.variationOption;

import com.applestore.backend.variation.Variation;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "variation_option")
public class VariationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String value;

    @ManyToOne()
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;
}
