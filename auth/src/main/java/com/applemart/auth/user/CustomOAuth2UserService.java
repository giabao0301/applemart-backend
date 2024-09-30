package com.applemart.auth.user;

import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.user.role.Role;
import com.applemart.auth.user.role.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String providerId = oauth2User.getName();
        String provider = userRequest.getClientRegistration().getRegistrationId();

        Optional<User> userOptional = userRepository.findByEmail(email);

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();

            if (!user.getExternalIds().containsKey(provider)) {
                user.getExternalIds().put(provider, providerId);
                user.setEnabled(true);
                userRepository.save(user);
            }
        } else {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            roles.add(role);

            Map<String, String> externalIds = new HashMap<>();
            externalIds.put(provider, providerId);


            user = User.builder()
                    .email(email)
                    .username(email)
                    .fullName(oauth2User.getAttribute("name"))
                    .roles(roles)
                    .externalIds(externalIds)
                    .enabled(true)
                    .build();

            userRepository.save(user);
        }


        return new CustomOAuth2User(user, oauth2User.getAuthorities());
    }
}
