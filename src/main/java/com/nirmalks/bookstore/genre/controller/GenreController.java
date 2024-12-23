package com.nirmalks.bookstore.genre.controller;

import com.nirmalks.bookstore.genre.dto.GenreDto;
import com.nirmalks.bookstore.genre.api.GenreRequest;
import com.nirmalks.bookstore.common.PageRequestDto;
import com.nirmalks.bookstore.genre.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public Page<GenreDto> getAllGenres(PageRequestDto pageRequestDto) {
        return genreService.getAllGenres(pageRequestDto);
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreRequest GenreRequest) {
        var genre = genreService.createGenre(GenreRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(genre.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).body(genre);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable Long id, @RequestBody GenreRequest GenreRequest) {
        return genreService.updateGenre(id, GenreRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
