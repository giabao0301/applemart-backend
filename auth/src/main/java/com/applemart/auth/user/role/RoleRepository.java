package com.applemart.auth.user.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String role);
    boolean existsByName(String role);
}
