package com.example.music_lab2.repository;

import com.example.music_lab2.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Optional<Genre> findByName(String name);
}
