package com.nirmalks.bookstore.author.dto;

import com.nirmalks.bookstore.common.AuthorRequest;
import com.nirmalks.bookstore.author.entity.Author;

public class AuthorMapper {
    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getBio());
    }

    public static Author toEntity(Author author, AuthorRequest authorRequest) {
        author.setName(authorRequest.getName());
        author.setBio(authorRequest.getBio());
        return author;
    }

    public static Author toEntity(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setBio(authorRequest.getBio());
        return author;
    }
}
