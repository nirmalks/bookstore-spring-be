package com.nirmalks.bookstore.book.controller;

import com.nirmalks.bookstore.book.api.BookRequest;
import com.nirmalks.bookstore.book.dto.BookDto;
import com.nirmalks.bookstore.book.service.BookService;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.common.RestPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public RestPage<BookDto> getAllBooks(PageRequestDto pageRequestDto
    ) {
        return bookService.getAllBooks(pageRequestDto);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookRequest BookRequest) {
        var Book = bookService.createBook(BookRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(Book.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(Book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookRequest BookRequest) {
        return bookService.updateBook(id, BookRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/genre/{name}")
    public Page<BookDto> getBookByGenre(@PathVariable String name,
    PageRequestDto pageRequestDto) {
        return bookService.getBooksByGenre(name, pageRequestDto);
    }

    @GetMapping("/author/{id}")
    public Page<BookDto> getBookByAuthor(@PathVariable Long id,
                                         @PathVariable String name,
                                         PageRequestDto pageRequestDto) {
        return bookService.getBooksByAuthor(id, pageRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/{quantity}")
    public void updateBookStock(@PathVariable Long id, @PathVariable int quantity) {
        bookService.updateBookStock(id, quantity);
    }

    @GetMapping("/search")
    public Page<BookDto> searchBooks(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookService.getFilteredBooks(search, genre, startDate, endDate, minPrice, maxPrice, sortBy, sortOrder, page, size);
    }
}