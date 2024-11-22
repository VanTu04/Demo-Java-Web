package com.project.shopapp.services;

import com.project.shopapp.DTO.UserDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.AuthResponse;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;
    AuthResponse login(String phoneNumber, String password) throws Exception;
    User findUserById(Long id) throws Exception;
}

