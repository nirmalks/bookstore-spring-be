package com.nirmalks.bookstore.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirmalks.bookstore.NoSecurityConfig;
import com.nirmalks.bookstore.author.entity.Author;
import com.nirmalks.bookstore.author.repository.AuthorRepository;
import com.nirmalks.bookstore.book.api.BookRequest;
import com.nirmalks.bookstore.genre.entity.Genre;
import com.nirmalks.bookstore.genre.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(NoSecurityConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateBook() throws Exception {
        Author author = new Author();
        author.setName("Default Author");
        author.setBio("Bio for default author");
        author = authorRepository.save(author);

        Genre genre = new Genre();
        genre.setName("Default Genre");
        genre = genreRepository.save(genre);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Spring Boot in Action");
        bookRequest.setAuthorIds(List.of(author.getId()));
        bookRequest.setGenreIds(List.of(genre.getId()));
        bookRequest.setPrice(29.99);
        bookRequest.setStock(10);
        bookRequest.setIsbn("1234567890123");
        bookRequest.setPublishedDate(LocalDate.now());
        bookRequest.setDescription("A great book on Spring Boot");
        bookRequest.setImagePath("/images/spring-boot.jpg");

        String bookJson = objectMapper.writeValueAsString(bookRequest);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Spring Boot in Action"))
                .andExpect(jsonPath("$.price").value(29.99))
                .andExpect(jsonPath("$.stock").value(10));
    }
}
