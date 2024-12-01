package com.applemart.auth.user.address;

import com.applemart.auth.user.User;
import jakarta.persistence.*;
import lombok.*;

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
    private String recipient;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String address;
    private String addressType;
    private Boolean isDeliveryAddress;
}
