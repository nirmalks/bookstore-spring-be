package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.*;
import com.nirmalks.bookstore.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Page<UserResponse> getUsers(PageRequestDto pageRequestDto);
    UserResponse createUser(CreateUserRequest userRequest, UserRole userRole);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest userRequest);
    void deleteUser(Long userId);
    LoginResponse authenticate(String username, String password);
}
