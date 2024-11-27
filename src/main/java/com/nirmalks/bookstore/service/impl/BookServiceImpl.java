package com.nirmalks.bookstore.service.impl;

import com.nirmalks.bookstore.dto.BookDto;
import com.nirmalks.bookstore.dto.BookRequest;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.mapper.BookMapper;
import com.nirmalks.bookstore.repository.BookRepository;
import com.nirmalks.bookstore.service.AuthorService;
import com.nirmalks.bookstore.service.BookService;
import com.nirmalks.bookstore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookMapper::toDTO).toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id).map(BookMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @Override
    public BookDto createBook(BookRequest bookRequest) {
        var authors = authorService.getAuthorsByIds(bookRequest.getAuthorIds());
        var genres = genreService.getGenresByIds(bookRequest.getGenreIds());
        return BookMapper.toDTO(bookRepository.save(BookMapper.toEntity(bookRequest, authors, genres)));
    }

    @Override
    public BookDto updateBook(Long id, BookRequest bookRequest) {
        var existingBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book not found"));
        var authors = authorService.getAuthorsByIds(bookRequest.getAuthorIds());
        var genres = genreService.getGenresByIds(bookRequest.getGenreIds());
        return BookMapper.toDTO(bookRepository.save(BookMapper.toEntity(existingBook, bookRequest, authors, genres)));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book not found"));
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getBooksByGenre(String genre) {
        genreService.validateGenreName(genre);
        var books = bookRepository.findAllByGenresName(genre);
        return books.stream().map(BookMapper::toDTO).toList();
    }

    @Override
    public List<BookDto> getBooksByAuthor(Long authorId) {
        authorService.getAuthorById(authorId);
        var books = bookRepository.findAllByAuthorsId(authorId);
        return books.stream().map(BookMapper::toDTO).toList();
    }

    @Override
    public void updateBookStock(Long bookId, int quantity) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        book.setStock(quantity);
        bookRepository.save(book);
    }
}
