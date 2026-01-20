package com.example.music_lab2.dto;

import com.example.music_lab2.model.SimplifiedGenre;
import java.util.UUID;

public class GenreListDto {
    private final UUID id;
    private final String name;


    public GenreListDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GenreListDto fromEntity(SimplifiedGenre entity) {
        return new GenreListDto(
                entity.getId(),
                entity.getName()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}