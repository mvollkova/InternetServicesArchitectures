package com.example.music_lab2.controller;

import com.example.music_lab2.dto.GenreCreateUpdateDto;
import com.example.music_lab2.dto.GenreListDto;
import com.example.music_lab2.dto.GenreReadDto;
import com.example.music_lab2.model.Genre;
import com.example.music_lab2.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;


    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping
    public List<GenreListDto> findAll() {
        return genreService.findAll().stream()
                .map(GenreListDto::fromEntity)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public GenreReadDto findById(@PathVariable UUID id) {
        return genreService.findById(id)
                .map(GenreReadDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with ID: " + id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
    public GenreReadDto create(@RequestBody GenreCreateUpdateDto dto) {

        Genre newGenre = new Genre(dto.getName());


        Genre savedGenre = genreService.create(newGenre);
        return GenreReadDto.fromEntity(savedGenre);
    }


    @PutMapping("/{id}")
    public GenreReadDto update(@PathVariable UUID id, @RequestBody GenreCreateUpdateDto dto) {

        Genre existingGenre = genreService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found for update with ID: " + id));


        existingGenre.setName(dto.getName());


        Genre updatedGenre = genreService.update(existingGenre);
        return GenreReadDto.fromEntity(updatedGenre);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // HTTP 204
    public void delete(@PathVariable UUID id) {
        genreService.delete(id);
    }
}