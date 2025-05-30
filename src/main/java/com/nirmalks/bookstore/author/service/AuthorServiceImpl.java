package com.nirmalks.bookstore.author.service;

import com.nirmalks.bookstore.author.dto.AuthorDto;
import com.nirmalks.bookstore.author.dto.AuthorMapper;
import com.nirmalks.bookstore.author.repository.AuthorRepository;
import com.nirmalks.bookstore.common.AuthorRequest;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.author.entity.Author;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nirmalks.bookstore.common.RequestUtils.getPageable;

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
    public Page<AuthorDto> getAllAuthors(PageRequestDto pageRequestDto) {
        return authorRepository.findAll(getPageable(pageRequestDto))
                .map(AuthorMapper::toDto);
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
