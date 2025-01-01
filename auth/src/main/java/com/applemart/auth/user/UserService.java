package com.applemart.auth.user;

import com.applemart.auth.common.PageResponse;
import com.applemart.auth.user.address.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    PageResponse<UserDTO> getUsers(int page, int size, String sort, String dir);
    UserDTO getUserProfile(Integer id);
    UserDTO getUserById(Integer id);
    UserDTO getUserByEmail(String email);
    void createUser(UserCreationRequest request);
    UserDTO updateUserProfile(UserUpdateRequest request);
    UserDTO updateUserById(Integer id, UserDTO request);
    void deleteUserById(Integer id);
    Optional<User> isUserExist(String identifier);

//    for user address
    List<AddressDTO> getAddress();
    List<AddressDTO> getAddressesByUserId(Integer userId);
    AddressDTO getAddressById(Integer userId, Integer id);
    AddressDTO addAddress(AddressDTO request);
    AddressDTO updateAddress(Integer id, AddressDTO request);
    void setDefaultAddress(Integer id);
    void deleteAddressById(Integer id);
}
