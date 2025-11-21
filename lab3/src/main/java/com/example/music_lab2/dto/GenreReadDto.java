package com.example.music_lab2.dto;

import com.example.music_lab2.model.Genre;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class GenreReadDto {
    private final UUID id;
    private final String name;

    private final List<SongListDto> songs;

    public GenreReadDto(UUID id, String name, List<SongListDto> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public static GenreReadDto fromEntity(Genre entity) {
        return new GenreReadDto(
                entity.getId(),
                entity.getName(),
                entity.getSongs().stream()
                        .map(SongListDto::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SongListDto> getSongs() {
        return songs;
    }
}