package com.applemart.auth.user;

import com.applemart.auth.common.PageResponse;
import com.applemart.auth.user.address.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    PageResponse<UserDTO> getUsers(int page, int size, String sort, String dir);
    UserDTO getUserProfile();
    UserDTO getUserById(Integer id);
    UserDTO getUserByEmail(String email);
    void createUser(UserCreationRequest request);
    UserDTO updateUser(Integer id, UserUpdateRequest request);
    void deleteUserById(Integer id);
    Optional<User> isUserExist(String identifier);

//    for user address
    List<AddressDTO> getAddress();
    AddressDTO getAddressById(Integer id);
    AddressDTO addAddress(AddressDTO request);
    AddressDTO updateAddress(Integer id, AddressDTO request);
    void deleteAddressById(Integer id);
}
