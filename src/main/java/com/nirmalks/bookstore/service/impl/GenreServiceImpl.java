package com.nirmalks.bookstore.service.impl;

import com.nirmalks.bookstore.dto.GenreDto;
import com.nirmalks.bookstore.dto.GenreRequest;
import com.nirmalks.bookstore.entity.Genre;
import com.nirmalks.bookstore.exception.ResourceNotFoundException;
import com.nirmalks.bookstore.mapper.GenreMapper;
import com.nirmalks.bookstore.repository.GenreRepository;
import com.nirmalks.bookstore.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public GenreDto createGenre(GenreRequest genreRequest) {
        var genre = genreRepository.save(GenreMapper.toEntity(genreRequest));
        return GenreMapper.toDto(genre);
    }

    @Override
    public GenreDto updateGenre(Long id, GenreRequest genreRequest) {
        var existingGenre = genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("genre not found"));
        var genre = genreRepository.save(GenreMapper.toEntity(existingGenre, genreRequest));
        return GenreMapper.toDto(genre);
    }

    @Override
    public GenreDto getGenreById(Long id) {
        return genreRepository.findById(id).map(GenreMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("genre not found"));
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreMapper::toDto).toList();
    }

    @Override
    public void deleteGenreById(Long id) {
        genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("genre not found"));
        genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> getGenresByIds(List<Long> ids) {
        var genres = genreRepository.findAllById(ids);
        if (genres.size() != ids.size()) {
            var foundIds = genres.stream().map(Genre::getId).toList();
            var missingIds = ids.stream().map((id) -> !foundIds.contains(id));
            throw new ResourceNotFoundException("Ids not found" + missingIds);
        }
        return genres;
    }

    @Override
    public void validateGenreName(String name) {
        genreRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("genre not found"));
    }
}
