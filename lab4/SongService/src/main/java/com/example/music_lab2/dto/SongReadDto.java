package com.example.music_lab2.dto;

import com.example.music_lab2.model.Song;
import java.util.UUID;

public class SongReadDto {
    private final UUID id;
    private final String title;
    private final String artist;
    private final int durationSeconds;

    private final GenreListDto genre;

    public SongReadDto(UUID id, String title, String artist, int durationSeconds, GenreListDto genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.genre = genre;
    }

    public static SongReadDto fromEntity(Song entity) {
        return new SongReadDto(
                entity.getId(),
                entity.getTitle(),
                entity.getArtist(),
                entity.getDurationSeconds(),

                GenreListDto.fromEntity(entity.getGenre())
        );
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public GenreListDto getGenre() {
        return genre;
    }
}