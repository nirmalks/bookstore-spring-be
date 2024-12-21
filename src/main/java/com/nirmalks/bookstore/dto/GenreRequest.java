package com.nirmalks.bookstore.dto;

import jakarta.validation.constraints.NotNull;

public class GenreRequest {
    @NotNull
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
