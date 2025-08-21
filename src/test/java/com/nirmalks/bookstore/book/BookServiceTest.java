package com.nirmalks.bookstore.book;

import com.nirmalks.bookstore.book.dto.BookDto;
import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.book.repository.BookRepository;
import com.nirmalks.bookstore.book.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookById_whenBookExists_returnsBookDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setPrice(30.0);
        book.setStock(10);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookDto result = bookService.getBookById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void getBookById_whenBookDoesNotExist_throwsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookById(99L));
    }
}
