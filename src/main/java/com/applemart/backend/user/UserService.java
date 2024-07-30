package com.applemart.backend.user;

import com.applemart.backend.user.address.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUserProfile();
    UserDTO getUserById(Integer id);
    void createUser(UserRegistrationRequest request);
    UserDTO updateUser(Integer id, UserUpdateRequest request);
    void deleteUserById(Integer id);

    List<AddressDTO> getAddress();
    AddressDTO addAddress(AddressDTO request);
    AddressDTO updateAddress(Integer id, AddressDTO request);
    void deleteAddressById(Integer id);
}
