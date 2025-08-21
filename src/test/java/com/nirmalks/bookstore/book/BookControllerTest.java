package com.nirmalks.bookstore.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirmalks.bookstore.book.api.BookRequest;
import com.nirmalks.bookstore.book.controller.BookController;
import com.nirmalks.bookstore.book.dto.BookDto;
import com.nirmalks.bookstore.book.service.BookService;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.common.RestPage;
import com.nirmalks.bookstore.security.filter.JwtTokenValidatorFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtTokenValidatorFilter.class
        ),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDto sampleBook() {
        return new BookDto(
                1L,
                "Test Book",
                List.of(1L, 2L),
                100.0,
                5,
                "1234",
                LocalDate.now(),
                List.of(1L, 2L),
                "desc",
                "img.jpg"
        );
    }

    @Test
    @WithMockUser
    void getBookById_shouldReturnBook() throws Exception {
        Mockito.when(bookService.getBookById(1L)).thenReturn(sampleBook());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    @WithMockUser
    void getAllBooks_shouldReturnBooks() throws Exception{
        RestPage<BookDto> dto = new RestPage<>(new PageImpl<>(List.of(sampleBook())));
        Mockito.when(bookService.getAllBooks(any(PageRequestDto.class))).thenReturn(dto);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Book"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook_shouldReturnCreatedBook() throws Exception {
        BookRequest request = new BookRequest();
        request.setTitle("New Book");
        request.setPrice(150.0);
        request.setStock(3);

        BookDto dto = new BookDto(2L, "New Book", List.of(1L,2L), 150.0, 3, "5678", LocalDate.now(),List.of(1L,2L), "desc", "img.jpg");
        Mockito.when(bookService.createBook(any(BookRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBook_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void searchBooks_shouldReturnBooks() throws Exception {
        RestPage<BookDto> dto = new RestPage<>(new PageImpl<>(List.of(sampleBook())));

        Mockito.when(bookService.getFilteredBooks(
                Mockito.eq("test"),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.eq(0),
                Mockito.eq(10)
        )).thenReturn(dto);

        mockMvc.perform(get("/api/books/search")
                        .param("search", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Book"));
    }
}
