package com.nirmalks.bookstore.book.service;

import com.nirmalks.bookstore.book.dto.BookDto;
import com.nirmalks.bookstore.book.api.BookRequest;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.book.BookSpecification;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.book.dto.BookMapper;
import com.nirmalks.bookstore.book.repository.BookRepository;
import com.nirmalks.bookstore.author.service.AuthorService;
import com.nirmalks.bookstore.genre.service.GenreService;
import com.nirmalks.bookstore.common.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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

    @Override
    public Page<BookDto> getFilteredBooks(String searchParam,
                                                    String genre,
                                                    LocalDate startDate,
                                                    LocalDate endDate,
                                                    Double minPrice,
                                                    Double maxPrice,
                                                    int page,
                                                    int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Book> specification = BookSpecification.filterBy(searchParam, genre,
                startDate, endDate, minPrice, maxPrice);
        System.out.println(specification);
        Page<Book> result = bookRepository.findAll(specification, pageable);
        System.out.println(result);
        return bookRepository.findAll(specification, pageable).map(BookMapper::toDTO);

    }
}
