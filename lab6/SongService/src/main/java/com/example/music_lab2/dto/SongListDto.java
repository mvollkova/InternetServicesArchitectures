package com.example.music_lab2.dto;

import com.example.music_lab2.model.Song;
import java.util.UUID;

public class SongListDto {
    private final UUID id;
    private final String title;
    private final String artist;
    private final int durationSeconds;

    public SongListDto(UUID id, String title, String artist, int durationSeconds) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    public static SongListDto fromEntity(Song entity) {
        return new SongListDto(
                entity.getId(),
                entity.getTitle(),
                entity.getArtist(),
                entity.getDurationSeconds() // Маппинг из сущности
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
}