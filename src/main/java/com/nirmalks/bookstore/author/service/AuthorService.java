package com.nirmalks.bookstore.author.service;

import com.nirmalks.bookstore.author.dto.AuthorDto;
import com.nirmalks.bookstore.author.api.AuthorRequest;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.author.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    AuthorDto createAuthor(AuthorRequest authorRequest);
    AuthorDto updateAuthor(Long id, AuthorRequest authorRequest);
    AuthorDto getAuthorById(Long id);
    Page<AuthorDto> getAllAuthors(PageRequestDto pageRequestDto);
    void deleteAuthorById(Long id);
    List<Author> getAuthorsByIds(List<Long> ids);
}
