package com.nirmalks.bookstore.repository;

import com.nirmalks.bookstore.entity.Author;
import com.nirmalks.bookstore.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
