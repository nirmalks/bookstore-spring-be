package com.nirmalks.bookstore.repository;

import com.nirmalks.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.name = :genreName")
    Page<Book> findAllByGenresName(@Param("genreName") String genreName, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    Page<Book> findAllByAuthorsId(@Param("authorId") Long authorId, Pageable pageable);
}
