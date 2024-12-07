package com.nirmalks.bookstore.controller;

import com.nirmalks.bookstore.dto.BookDto;
import com.nirmalks.bookstore.dto.BookRequest;
import com.nirmalks.bookstore.dto.PageRequestDto;
import com.nirmalks.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public Page<BookDto> getAllBooks(PageRequestDto pageRequestDto
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

    @GetMapping("/{name}")
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
}
