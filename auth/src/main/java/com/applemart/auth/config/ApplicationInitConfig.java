package com.applemart.auth.config;

import com.applemart.auth.user.role.Role;
import com.applemart.auth.user.role.RoleRepository;
import com.applemart.auth.user.User;
import com.applemart.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                Set<Role> roles = new HashSet<>();

                if (!roleRepository.existsByName("ADMIN")) {
                    Role role = new Role();
                    role.setName("ADMIN");
                    roleRepository.save(role);
                }

                roles.add(roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new RuntimeException("No admin role found")));

                User user = User.builder()
                        .username("admin")
                        .email("admin@apple.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .enabled(true)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password: admin, please change it");
            }
        };
    }
}