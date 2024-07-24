package com.applemart.backend.user;

import com.applemart.backend.user.address.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUserProfile();
    void createUser(UserRegistrationRequest request);
    void deleteUserById(Integer id);
    UserDTO updateUser(UserUpdateRequest request);

    List<AddressDTO> getAddress();
    AddressDTO addAddress(AddressDTO request);
}
