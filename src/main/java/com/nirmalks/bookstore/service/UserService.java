package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.CreateUserRequest;
import com.nirmalks.bookstore.dto.UpdateUserRequest;
import com.nirmalks.bookstore.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponse> getUsers();
    UserResponse createUser(CreateUserRequest userRequest);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest userRequest);
    void deleteUser(Long userId);
}
