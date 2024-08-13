package com.applemart.auth.user;

import com.applemart.auth.registration.RegistrationRequest;
import com.applemart.auth.user.address.AddressDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUserProfile();
    UserDTO getUserById(Integer id);
    void createUser(UserCreationRequest request);
    UserDTO updateUser(Integer id, UserUpdateRequest request);
    void deleteUserById(Integer id);


//    for user address
    List<AddressDTO> getAddress();
    AddressDTO addAddress(AddressDTO request);
    AddressDTO updateAddress(Integer id, AddressDTO request);
    void deleteAddressById(Integer id);
}
