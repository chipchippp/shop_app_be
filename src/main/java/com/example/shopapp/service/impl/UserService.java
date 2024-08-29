package com.example.shopapp.service.impl;

import com.example.shopapp.dto.UserDTO;
import com.example.shopapp.dto.UserLoginDTO;
import com.example.shopapp.entity.UserEntity;
import com.example.shopapp.response.AuthResponse;

public interface UserService {
    UserEntity findByUserByJwtToken(String jwt) throws Exception;
    UserEntity findUserByEmail(String email) throws Exception;
    AuthResponse login(UserLoginDTO userLoginDTO) throws Exception;
    AuthResponse register(UserDTO userDTO) throws Exception;
}
