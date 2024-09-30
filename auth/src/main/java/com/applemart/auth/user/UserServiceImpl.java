package com.applemart.auth.user;

import com.applemart.auth.clients.EmailValidationService;
import com.applemart.auth.exception.DuplicateResourceException;
import com.applemart.auth.exception.RequestValidationException;
import com.applemart.auth.exception.ResourceNotFoundException;
import com.applemart.auth.registration.token.ConfirmationToken;
import com.applemart.auth.registration.token.ConfirmationTokenRepository;
import com.applemart.auth.response.PageResponse;
import com.applemart.auth.user.address.Address;
import com.applemart.auth.user.address.AddressDTO;
import com.applemart.auth.user.address.AddressDTOMapper;
import com.applemart.auth.user.address.AddressRepository;
import com.applemart.auth.user.role.Role;
import com.applemart.auth.user.role.RoleRepository;
import com.applemart.auth.utils.OTPGenerator;
import com.applemart.clients.notification.NotificationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    private final EmailValidationService emailValidationService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    //    Lấy user đang đăng nhập.
    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String id = authentication.getName();

        return userRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(id)));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserDTO> getUsers(int page, int size, String sort, String dir) {

        Sort sortBy = dir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();

        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<User> pages = userRepository.findAll(pageable);

        List<UserDTO> userDTOs = pages.getContent()
                .stream()
                .map(userDTOMapper::toDTO)
                .toList();

        return PageResponse.<UserDTO>builder()
                .currentPage(page)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .content(userDTOs)
                .build();
    }

    @Override
    @Transactional
    public UserDTO getUserProfile() {
        return userDTOMapper.toDTO(getLoggedInUser());
    }

    @Override
    @Transactional
    @PostAuthorize("returnObject.id.toString() == authentication.name or hasRole('ADMIN')")
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%d] not found".formatted(id)));

        return userDTOMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [%s] not found".formatted(email)));

        return userDTOMapper.toDTO(user);
    }


    @Override
    public void createUser(UserCreationRequest request) {

        if (emailValidationService.validateEmail(request.getEmail())) {
            throw new ResourceNotFoundException("Email doesn't exist");
        }

        User user = userDTOMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already in use");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (roleRepository.existsByName(role.getName())) {
                throw new ResourceNotFoundException("Role [%s] not found".formatted(role.getName()));
            }
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    @PostAuthorize("returnObject.id == authentication.name or hasRole('ADMIN')")
    public UserDTO updateUser(Integer id, UserUpdateRequest request) {
        if (emailValidationService.validateEmail(request.getEmail())) {
            throw new ResourceNotFoundException("Email doesn't exist");
        }

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

        String token = OTPGenerator.generateOTP(6);
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenRepository.save(confirmationToken);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message(token)
                .toUserEmail(user.getEmail())
                .toUserName(user.getFullName())
                .build();

        kafkaTemplate.send("notification", notificationRequest);

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
    public Optional<User> isUserExist(String identifier) {
        Optional<User> userWithUsername = userRepository.findByUsername(identifier);

        if (userWithUsername.isPresent()) {
            return userWithUsername;
        }

        Optional<User> userWithEmail = userRepository.findByEmail(identifier);
        if (userWithEmail.isPresent()) {
            return userWithEmail;
        }

        Optional<User> userWithPhone = userRepository.findByPhoneNumber(identifier);
        if (userWithPhone.isPresent()) {
            return userWithPhone;
        }

        return Optional.empty();
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
