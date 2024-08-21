package com.applemart.auth.user;

import com.applemart.auth.user.address.Address;
import com.applemart.auth.user.role.Role;
import com.applemart.auth.validator.Username;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String profileImageUrl;

    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean enabled;

    @ElementCollection
    @CollectionTable(name = "user_external_ids", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "provider")
    @Column(name = "external_id")
    @JsonIgnore
    private Map<String, String> externalIds = new HashMap<>();

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();
}
