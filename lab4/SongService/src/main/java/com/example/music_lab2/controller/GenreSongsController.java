package com.example.music_lab2.controller;

import com.example.music_lab2.dto.SongCreateUpdateDto;
import com.example.music_lab2.dto.SongListDto;
import com.example.music_lab2.dto.SongReadDto;
import com.example.music_lab2.model.SimplifiedGenre;
import com.example.music_lab2.model.Song;
import com.example.music_lab2.repository.SimplifiedGenreRepository;
import com.example.music_lab2.repository.SongRepository;
import com.example.music_lab2.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreSongsController {

    private final SongRepository songRepository;
    private final SimplifiedGenreRepository genreRepository;
    private final SongService songService;

    public GenreSongsController(
            SongRepository songRepository,
            SimplifiedGenreRepository genreRepository,
            SongService songService
    ) {
        this.songRepository = songRepository;
        this.genreRepository = genreRepository;
        this.songService = songService;
    }

    // GET /api/genres/{genreId}/songs
    @GetMapping("/{genreId}/songs")
    public List<SongListDto> getSongsByGenre(@PathVariable UUID genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with ID: " + genreId);
        }

        return songRepository.findAllByGenre_Id(genreId).stream()
                .map(SongListDto::fromEntity)
                .collect(Collectors.toList());
    }

    // POST /api/genres/{genreId}/songs
    @PostMapping("/{genreId}/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongReadDto createSongInGenre(@PathVariable UUID genreId,
                                         @RequestBody SongCreateUpdateDto dto) {
        SimplifiedGenre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found with ID: " + genreId));

        Song newSong = new Song(dto.getTitle(), dto.getArtist(), dto.getDurationSeconds(), genre);
        return SongReadDto.fromEntity(songService.create(newSong));
    }
}
