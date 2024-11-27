package com.nirmalks.bookstore.service;

import com.nirmalks.bookstore.dto.GenreDto;
import com.nirmalks.bookstore.dto.GenreRequest;
import com.nirmalks.bookstore.entity.Genre;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface GenreService {
    GenreDto createGenre(GenreRequest genreRequest);

    GenreDto updateGenre(Long id, GenreRequest genreRequest);

    GenreDto getGenreById(Long id);

    List<GenreDto> getAllGenres();

    void deleteGenreById(Long id);

    List<Genre> getGenresByIds(List<Long> ids);

    void validateGenreName(String name);
}

