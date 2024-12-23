package com.nirmalks.bookstore.user.service;

import com.nirmalks.bookstore.auth.api.LoginResponse;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.user.api.CreateUserRequest;
import com.nirmalks.bookstore.user.api.UpdateUserRequest;
import com.nirmalks.bookstore.user.api.UserResponse;
import com.nirmalks.bookstore.user.entity.UserRole;
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
