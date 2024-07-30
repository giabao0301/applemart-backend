package com.applemart.backend.user;

import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.RequestValidationException;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.user.address.Address;
import com.applemart.backend.user.address.AddressDTO;
import com.applemart.backend.user.address.AddressDTOMapper;
import com.applemart.backend.user.address.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
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
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final AddressDTOMapper addressDTOMapper;
    private final RoleRepository roleRepository;
    private final UserDTOMapper userDTOMapper;

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
                .map(userDTOMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserProfile() {
        return userDTOMapper.toDTO(getLoggedInUser());
    }

    @Override
    @Transactional
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%d] not found".formatted(id)));

        return userDTOMapper.toDTO(user);
    }


    @Override
    public void createUser(UserRegistrationRequest request) {
        User user = userDTOMapper.toEntity(request);

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
    @Transactional
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserDTO updateUser(Integer id, UserUpdateRequest request) {
        User user = userRepository.getReferenceById(id);

        User userUpdateRequest = userDTOMapper.toEntity(request);

        boolean changed = false;

        if (userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().equals(user.getUsername())) {
            user.setUsername(userUpdateRequest.getUsername());
            changed = true;
        }

        if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            user.setEmail(userUpdateRequest.getEmail());
            changed = true;
        }

        if (userUpdateRequest.getFullName() != null && !userUpdateRequest.getFullName().equals(user.getFullName())) {
            user.setFullName(userUpdateRequest.getFullName());
            changed = true;
        }

        if (userUpdateRequest.getDateOfBirth() != null && !userUpdateRequest.getDateOfBirth().equals(user.getDateOfBirth())) {
            user.setDateOfBirth(userUpdateRequest.getDateOfBirth());
            changed = true;
        }

        if (userUpdateRequest.getPhoneNumber() != null && !userUpdateRequest.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            changed = true;
        }

        if (userUpdateRequest.getProfileImageUrl() != null && !userUpdateRequest.getProfileImageUrl().equals(user.getProfileImageUrl())) {
            user.setProfileImageUrl(userUpdateRequest.getProfileImageUrl());
            changed = true;
        }

        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        return userDTOMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%d] not found]".formatted(id)));

        Role role = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (!getLoggedInUser().getUsername().equals(user.getUsername()) || !getLoggedInUser().getRoles().contains(role)) {
            throw new AccessDeniedException("You don't have permission to delete this user");
        }

        userRepository.delete(user);
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

    @Override
    public AddressDTO updateAddress(Integer id, AddressDTO request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id [%d] not found".formatted(id)));

        Address addressUpdateRequest = addressDTOMapper.toEntity(request);

        boolean changed = false;

        if (addressUpdateRequest.getPhone() != null && !addressUpdateRequest.getPhone().equals(address.getPhone())) {
            address.setPhone(addressUpdateRequest.getPhone());
            changed = true;
        }

        if (addressUpdateRequest.getCity() != null && !addressUpdateRequest.getCity().equals(address.getCity())) {
            address.setCity(addressUpdateRequest.getCity());
            changed = true;
        }

        if (addressUpdateRequest.getDistrict() != null && !addressUpdateRequest.getDistrict().equals(address.getDistrict())) {
            address.setDistrict(addressUpdateRequest.getDistrict());
            changed = true;
        }

        if (addressUpdateRequest.getWard() != null && !addressUpdateRequest.getWard().equals(address.getWard())) {
            address.setWard(addressUpdateRequest.getWard());
            changed = true;
        }

        if (addressUpdateRequest.getAddress() != null && !addressUpdateRequest.getAddress().equals(address.getAddress())) {
            address.setAddress(addressUpdateRequest.getAddress());
            changed = true;
        }

        if (addressUpdateRequest.getAddressType() != null && !addressUpdateRequest.getAddressType().equals(address.getAddressType())) {
            address.setAddressType(addressUpdateRequest.getAddressType());
            changed = true;
        }

        if (addressUpdateRequest.getIsDeliveryAddress() != null && !addressUpdateRequest.getIsDeliveryAddress().equals(address.getIsDeliveryAddress())) {
            address.setIsDeliveryAddress(addressUpdateRequest.getIsDeliveryAddress());
            changed = true;
        }

        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        return addressDTOMapper.toDTO(addressRepository.save(address));
    }


    @Override
    @Transactional
    public void deleteAddressById(Integer id) {
        User user = getLoggedInUser();

        List<Address> addresses = addressRepository.findByUser(user);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id [%d] not found".formatted(id)));

        if (!addresses.contains(address)) {
            throw new AccessDeniedException("You don't have permission to delete this address");
        }

        addressRepository.delete(address);
    }
}
