package com.nirmalks.bookstore.controller;

import com.nirmalks.bookstore.dto.CreateUserRequest;
import com.nirmalks.bookstore.dto.UpdateUserRequest;
import com.nirmalks.bookstore.dto.UserResponse;
import com.nirmalks.bookstore.entity.UserRole;
import com.nirmalks.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid CreateUserRequest userRequest) {
        var userResponse = userService.createUser(userRequest, UserRole.CUSTOMER);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userResponse.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(userResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest userRequest, @PathVariable Long id) {
        var userResponse = userService.updateUser(id, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
