package com.nirmalks.bookstore.genre.service;

import com.nirmalks.bookstore.genre.dto.GenreDto;
import com.nirmalks.bookstore.genre.api.GenreRequest;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.genre.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {
    GenreDto createGenre(GenreRequest genreRequest);

    GenreDto updateGenre(Long id, GenreRequest genreRequest);

    GenreDto getGenreById(Long id);

    Page<GenreDto> getAllGenres(PageRequestDto pageRequestDto);

    void deleteGenreById(Long id);

    List<Genre> getGenresByIds(List<Long> ids);

    void validateGenreName(String name);
}

