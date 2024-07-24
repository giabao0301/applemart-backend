package com.applemart.backend.user;

import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.user.address.Address;
import com.applemart.backend.user.address.AddressDTO;
import com.applemart.backend.user.address.AddressDTOMapper;
import com.applemart.backend.user.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
    private final RoleRepository roleRepository;

    //    Lấy user đang đăng nhập.
    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserProfile() {
        return userMapper.toDTO(getLoggedInUser());
    }

    @Override
    public void createUser(UserRegistrationRequest request) {
        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already in use");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Address> addresses = addressRepository.findByUser(user);

        addressRepository.deleteAll(addresses);

        userRepository.delete(user);
    }

    @Override
    public UserDTO updateUser(UserUpdateRequest request) {
        User user = getLoggedInUser();

        return null;
    }

    @Override
    public List<AddressDTO> getAddress() {
        User user = getLoggedInUser();

        List<Address> addresses = addressRepository.findByUser(user);

        return addresses.stream()
                .map(addressDTOMapper::toDTO)
                .toList();
    }

    @Override
    public AddressDTO addAddress(AddressDTO request) {
        Address address = addressDTOMapper.toEntity(request);

        User user = getLoggedInUser();

        address.setUser(user);

        Address newAddress = addressRepository.save(address);
        return addressDTOMapper.toDTO(newAddress);
    }
}
