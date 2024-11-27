package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.BookDto;
import com.nirmalks.bookstore.dto.BookRequest;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(Long id);
    BookDto createBook(BookRequest bookRequest);
    BookDto updateBook(Long id, BookRequest bookRequest);
    void deleteBookById(Long id);
    List<BookDto> getBooksByGenre(String genre);
    List<BookDto> getBooksByAuthor(Long authorId);
    void updateBookStock(Long bookId, int quantity);
}
