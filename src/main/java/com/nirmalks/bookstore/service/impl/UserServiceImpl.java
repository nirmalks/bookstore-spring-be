package com.nirmalks.bookstore.service.impl;

import com.nirmalks.bookstore.dto.*;
import com.nirmalks.bookstore.entity.User;
import com.nirmalks.bookstore.entity.UserRole;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.mapper.UserMapper;
import com.nirmalks.bookstore.repository.UserRepository;
import com.nirmalks.bookstore.service.UserService;
import com.nirmalks.bookstore.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nirmalks.bookstore.utils.RequestUtils.getPageable;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
    public Page<UserResponse> getUsers(PageRequestDto pageRequestDto) {
        return userRepository.findAll(getPageable(pageRequestDto)).map(UserMapper::toResponse);
    }

    @Override
    public UserResponse createUser(CreateUserRequest userRequest, UserRole role) {
        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encryptedPassword);
        User user = UserMapper.toEntity(userRequest, role);
        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserMapper.toResponse(user.get());
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        User updatedUser = userRepository.save(UserMapper.toEntity(user.get(), userRequest));
        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public LoginResponse authenticate(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        String token =  JwtUtils.generateToken(user.getUsername(), user.getAuthorities());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUsername(user.getUsername());
        loginResponse.setUserId(user.getId());
        loginResponse.setRole(user.getRole().name());
        return loginResponse;

    }
}

