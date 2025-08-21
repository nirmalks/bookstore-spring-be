package com.nirmalks.bookstore.book;

import com.nirmalks.bookstore.book.entity.Book;
import com.nirmalks.bookstore.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAll_returnsBooks() {
        Book book1 = new Book();
        book1.setTitle("Spring Boot in Action");
        book1.setPrice(29.99);
        book1.setStock(10);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Java Concurrency in Practice");
        book2.setPrice(39.99);
        book2.setStock(5);
        bookRepository.save(book2);

        List<Book> results = bookRepository.findAll();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getTitle()).isEqualTo("Spring Boot in Action");
    }
}
