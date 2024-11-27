package com.nirmalks.bookstore.controller;

import com.nirmalks.bookstore.dto.BookDto;
import com.nirmalks.bookstore.dto.BookRequest;
import com.nirmalks.bookstore.service.BookService;
import com.nirmalks.bookstore.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookRequest BookRequest) {
        var Book = bookService.createBook(BookRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(Book.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(Book);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookRequest BookRequest) {
        return bookService.updateBook(id, BookRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{name}")
    public List<BookDto> getBookByGenre(@PathVariable String name) {
        return bookService.getBooksByGenre(name);
    }

    @GetMapping("/author/{id}")
    public List<BookDto> getBookByAuthor(@PathVariable Long id) {
        return bookService.getBooksByAuthor(id);
    }

    @PutMapping("/{id}/{quantity}")
    public void updateBookStock(@PathVariable Long id, @PathVariable int quantity) {
        bookService.updateBookStock(id, quantity);
    }
}
