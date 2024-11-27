package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.AuthorDto;
import com.nirmalks.bookstore.dto.AuthorRequest;
import com.nirmalks.bookstore.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    AuthorDto createAuthor(AuthorRequest authorRequest);
    AuthorDto updateAuthor(Long id, AuthorRequest authorRequest);
    AuthorDto getAuthorById(Long id);
    List<AuthorDto> getAllAuthors();
    void deleteAuthorById(Long id);
    List<Author> getAuthorsByIds(List<Long> ids);
}
