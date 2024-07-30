package com.applemart.backend.user.address;

import com.applemart.backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String phone;
    private String city;
    private String district;
    private String ward;
    private String address;
    private String addressType;
    private Boolean isDeliveryAddress;
}
