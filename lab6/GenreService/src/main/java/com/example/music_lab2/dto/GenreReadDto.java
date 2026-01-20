package com.example.music_lab2.dto;

import com.example.music_lab2.model.Genre;
import java.util.UUID;


public class GenreReadDto {
    private final UUID id;
    private final String name;


    public GenreReadDto(UUID id, String name) {
        this.id = id;
        this.name = name;

    }

    public static GenreReadDto fromEntity(Genre entity) {
        return new GenreReadDto(
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