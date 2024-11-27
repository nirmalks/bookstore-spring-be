package com.nirmalks.bookstore.service.impl;

import com.nirmalks.bookstore.dto.AuthorDto;
import com.nirmalks.bookstore.dto.AuthorRequest;
import com.nirmalks.bookstore.entity.Author;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.mapper.AuthorMapper;
import com.nirmalks.bookstore.repository.AuthorRepository;
import com.nirmalks.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDto createAuthor(AuthorRequest authorRequest) {
        Author author = AuthorMapper.toEntity(authorRequest);
        Author savedAuthor = authorRepository.save(author);
        return AuthorMapper.toDto(savedAuthor);
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorRequest authorRequest) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        Author savedAuthor = authorRepository.save(AuthorMapper.toEntity(existingAuthor, authorRequest));
        return AuthorMapper.toDto(savedAuthor);
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        return authorRepository.findById(id).map(AuthorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAuthorById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAuthorsByIds(List<Long> ids) {
        var authors = authorRepository.findAllById(ids);
        if(ids.size() != authors.size()) {
            var foundIds = authors.stream().map(Author::getId).toList();
            var missingIds = ids.stream().filter((id) -> !foundIds.contains(id)).toList();
            throw new ResourceNotFoundException("Authors not found for IDs: " + missingIds);
        }
        return authors;
    }
}
