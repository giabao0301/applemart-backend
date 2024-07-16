package com.applemart.backend.user;

import com.applemart.backend.common.response.ApiResponse;
import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.user.address.Address;
import com.applemart.backend.user.address.AddressDTO;
import com.applemart.backend.user.address.AddressDTOMapper;
import com.applemart.backend.user.address.AddressRepository;
import com.applemart.backend.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final AddressDTOMapper addressDTOMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public void addUser(UserRegistrationRequest request) {
        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already in use");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public List<AddressDTO> getAddressByUserId(Integer userId) {
        return addressRepository.findAll()
                .stream()
                .map(addressDTOMapper::toDTO)
                .toList();
    }

    @Override
    public AddressDTO addAddress(Integer userId, AddressDTO request) {
        Address address = addressDTOMapper.toEntity(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        address.setUser(user);

        Address newAddress = addressRepository.save(address);
        return addressDTOMapper.toDTO(newAddress);
    }
}
