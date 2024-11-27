package com.nirmalks.bookstore.dto;

import jakarta.validation.constraints.NotNull;

public class AuthorRequest {
    @NotNull
    private String name;

    @NotNull
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
