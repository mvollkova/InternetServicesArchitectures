package com.example.music_lab2.dto;

import java.util.UUID;

public class GenreEventDto {
    private UUID id;
    private String name;

    public GenreEventDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}