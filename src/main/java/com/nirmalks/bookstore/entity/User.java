package com.nirmalks.bookstore.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "App_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;

    @OneToMany
    private List<Order> orders;
}
