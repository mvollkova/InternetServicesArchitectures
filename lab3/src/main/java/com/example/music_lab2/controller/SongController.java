package com.example.music_lab2.controller;

import com.example.music_lab2.dto.SongCreateUpdateDto;
import com.example.music_lab2.dto.SongListDto;
import com.example.music_lab2.dto.SongReadDto;
import com.example.music_lab2.model.Genre;
import com.example.music_lab2.model.Song;
import com.example.music_lab2.service.GenreService;
import com.example.music_lab2.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class SongController {

    private final SongService songService;
    private final GenreService genreService;

    public SongController(SongService songService, GenreService genreService) {
        this.songService = songService;
        this.genreService = genreService;
    }


    @GetMapping("/api/songs")
    public List<SongListDto> findAllSongs() {
        return songService.findAll().stream()
                .map(SongListDto::fromEntity)
                .collect(Collectors.toList());
    }


    @GetMapping("/api/songs/{id}")
    public SongReadDto findSongById(@PathVariable UUID id) {
        return songService.findById(id)
                .map(SongReadDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found with ID: " + id));
    }


    @GetMapping("/api/genres/{genreId}/songs")
    public List<SongListDto> findSongsByGenre(@PathVariable UUID genreId) {

        Genre genre = genreService.findById(genreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with ID: " + genreId));


        return songService.findByGenre(genre).stream()
                .map(SongListDto::fromEntity)
                .collect(Collectors.toList());
    }


    @PostMapping("/api/genres/{genreId}/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongReadDto createSong(@PathVariable UUID genreId, @RequestBody SongCreateUpdateDto dto) {

        Genre genre = genreService.findById(genreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add song: Genre not found with ID: " + genreId));


        Song newSong = new Song(
                dto.getTitle(),
                dto.getArtist(),
                dto.getDurationSeconds(),
                genre
        );



        Song savedSong = songService.create(newSong);
        return SongReadDto.fromEntity(savedSong);
    }

    @PutMapping("/api/songs/{id}")
    public SongReadDto updateSong(@PathVariable UUID id, @RequestBody SongCreateUpdateDto dto) {
        Song existingSong = songService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found for update with ID: " + id));

        existingSong.setTitle(dto.getTitle());
        existingSong.setArtist(dto.getArtist());
        existingSong.setDurationSeconds(dto.getDurationSeconds());

        Song updatedSong = songService.update(existingSong);
        return SongReadDto.fromEntity(updatedSong);
    }

    @DeleteMapping("/api/songs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable UUID id) {
        songService.delete(id);
    }
}