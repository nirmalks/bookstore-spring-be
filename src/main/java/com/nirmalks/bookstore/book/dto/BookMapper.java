package com.nirmalks.bookstore.book.dto;

import com.nirmalks.bookstore.book.api.BookRequest;
import com.nirmalks.bookstore.author.entity.Author;
import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.genre.entity.Genre;

import java.util.List;

public class BookMapper {
    public static BookDto toDTO(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthors().stream().map((Author::getId)).toList(),
                book.getPrice(),
                book.getStock(),
                book.getIsbn(),
                book.getPublishedDate(),
                book.getGenres().stream().map((Genre::getId)).toList(),
                book.getDescription(),
                book.getImagePath()
        );
    }

    public static Book toEntity(BookRequest bookRequest, List<Author> authors, List<Genre> genres) {
        var book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthors(authors);
        book.setPrice(bookRequest.getPrice());
        book.setStock(bookRequest.getStock());
        book.setIsbn(bookRequest.getIsbn());
        book.setPublishedDate(bookRequest.getPublishedDate());
        book.setGenres(genres);
        book.setDescription(bookRequest.getDescription());
        book.setImagePath(bookRequest.getImagePath());
        return book;
    }

    public static Book toEntity(Book book, BookRequest bookRequest, List<Author> authors, List<Genre> genres) {
        book.setTitle(bookRequest.getTitle());
        book.setAuthors(authors);
        book.setPrice(bookRequest.getPrice());
        book.setStock(bookRequest.getStock());
        book.setIsbn(bookRequest.getIsbn());
        book.setPublishedDate(bookRequest.getPublishedDate());
        book.setGenres(genres);
        book.setDescription(bookRequest.getDescription());
        book.setImagePath(bookRequest.getImagePath());
        return book;
    }
}
