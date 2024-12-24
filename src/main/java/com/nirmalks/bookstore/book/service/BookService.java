package com.nirmalks.bookstore.book.service;

import com.nirmalks.bookstore.book.api.BookRequest;
import com.nirmalks.bookstore.book.dto.BookDto;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.common.RestPage;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BookService {
    RestPage<BookDto> getAllBooks(PageRequestDto pageRequestDto);
    BookDto getBookById(Long id);
    BookDto createBook(BookRequest bookRequest);
    BookDto updateBook(Long id, BookRequest bookRequest);
    void deleteBookById(Long id);
    Page<BookDto> getBooksByGenre(String genre, PageRequestDto pageRequestDto);
    Page<BookDto> getBooksByAuthor(Long authorId, PageRequestDto pageRequestDto);
    void updateBookStock(Long bookId, int quantity);

    Page<BookDto> getFilteredBooks(String searchParam, String genre, LocalDate startDate, LocalDate endDate, Double minPrice, Double maxPrice, int page, int size);
}
