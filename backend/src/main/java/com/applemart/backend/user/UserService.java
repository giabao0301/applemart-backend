package com.applemart.backend.user;

import com.applemart.backend.user.address.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers();
    void addUser(UserRegistrationRequest request);
    List<AddressDTO> getAddressByUserId(Integer userId);
    AddressDTO addAddress(Integer userId, AddressDTO request);
}
