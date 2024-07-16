package com.applemart.backend.user.address;

import com.applemart.backend.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.TrueFalseConverter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String phone;
    private String city;
    private String district;
    private String ward;
    private String address;

    @EqualsAndHashCode.Exclude
    private String addressType;

    @EqualsAndHashCode.Exclude
    private boolean isDeliveryAddress;

}
