package com.nirmalks.bookstore.service.impl;

import com.nirmalks.bookstore.dto.BookDto;
import com.nirmalks.bookstore.dto.BookRequest;
import com.nirmalks.bookstore.dto.PageRequestDto;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.mapper.BookMapper;
import com.nirmalks.bookstore.repository.BookRepository;
import com.nirmalks.bookstore.service.AuthorService;
import com.nirmalks.bookstore.service.BookService;
import com.nirmalks.bookstore.service.GenreService;
import com.nirmalks.bookstore.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Override
    public Page<BookDto> getAllBooks(PageRequestDto pageRequestDto) {
        Pageable pageable = RequestUtils.getPageable(pageRequestDto);
        return bookRepository.findAll(pageable).map(BookMapper::toDTO);
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
    public Page<BookDto> getBooksByGenre(String genre, PageRequestDto pageRequestDto) {
        genreService.validateGenreName(genre);
        var pageable = RequestUtils.getPageable(pageRequestDto);
        var books = bookRepository.findAllByGenresName(genre, pageable);
        return books.map(BookMapper::toDTO);
    }

    @Override
    public Page<BookDto> getBooksByAuthor(Long authorId, PageRequestDto pageRequestDto) {
        authorService.getAuthorById(authorId);
        var pageable = RequestUtils.getPageable(pageRequestDto);
        var books = bookRepository.findAllByAuthorsId(authorId, pageable);
        return books.map(BookMapper::toDTO);
    }

    @Override
    public void updateBookStock(Long bookId, int quantity) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        book.setStock(quantity);
        bookRepository.save(book);
    }
}
