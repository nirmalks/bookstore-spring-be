package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.CreateUserRequest;
import com.nirmalks.bookstore.dto.LoginResponse;
import com.nirmalks.bookstore.dto.UpdateUserRequest;
import com.nirmalks.bookstore.dto.UserResponse;
import com.nirmalks.bookstore.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponse> getUsers();
    UserResponse createUser(CreateUserRequest userRequest, UserRole userRole);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest userRequest);
    void deleteUser(Long userId);
    LoginResponse authenticate(String username, String password);
}
