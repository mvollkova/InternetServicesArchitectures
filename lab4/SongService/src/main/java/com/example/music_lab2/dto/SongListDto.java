package com.example.music_lab2.dto;

import com.example.music_lab2.model.Song;
import java.util.UUID;

public class SongListDto {
    private final UUID id;
    private final String title;
    private final String artist;

    public SongListDto(UUID id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public static SongListDto fromEntity(Song entity) {
        return new SongListDto(
                entity.getId(),
                entity.getTitle(),
                entity.getArtist()
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
}