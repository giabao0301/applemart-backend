package com.applemart.backend.config;

import com.applemart.backend.user.Role;
import com.applemart.backend.user.RoleRepository;
import com.applemart.backend.user.User;
import com.applemart.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new RuntimeException("Admin Role not found")));

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin, please change it");
            }
        };
    }
}