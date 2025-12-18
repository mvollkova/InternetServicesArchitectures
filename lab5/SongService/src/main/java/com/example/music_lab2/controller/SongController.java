package com.example.music_lab2.controller;

import com.example.music_lab2.dto.SongCreateUpdateDto;
import com.example.music_lab2.dto.SongListDto;
import com.example.music_lab2.dto.SongReadDto;
import com.example.music_lab2.model.Song;
import com.example.music_lab2.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;


    public SongController(SongService songService) {
        this.songService = songService;
    }


    @GetMapping // /api/songs
    public List<SongListDto> findAllSongs() {
        return songService.findAll().stream()
                .map(SongListDto::fromEntity)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}") // /api/songs/{id}
    public SongReadDto findSongById(@PathVariable UUID id) {
        return songService.findById(id)
                .map(SongReadDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found with ID: " + id));
    }



    @PostMapping // POST /api/songs
    @ResponseStatus(HttpStatus.CREATED)
    public SongReadDto createSong(@RequestBody SongCreateUpdateDto dto) {

        Song newSong = new Song(
                dto.getTitle(),
                dto.getArtist(),
                dto.getDurationSeconds(),
                null
        );

        Song savedSong = songService.create(newSong);
        return SongReadDto.fromEntity(savedSong);
    }

    @PutMapping("/{id}")
    public SongReadDto updateSong(@PathVariable UUID id, @RequestBody SongCreateUpdateDto dto) {
        Song existingSong = songService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found for update with ID: " + id));

        existingSong.setTitle(dto.getTitle());
        existingSong.setArtist(dto.getArtist());
        existingSong.setDurationSeconds(dto.getDurationSeconds());

        Song updatedSong = songService.update(existingSong);
        return SongReadDto.fromEntity(updatedSong);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable UUID id) {
        songService.delete(id);
    }
}