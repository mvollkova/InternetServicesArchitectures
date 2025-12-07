package com.example.music_lab2.repository;

import com.example.music_lab2.model.SimplifiedGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SimplifiedGenreRepository extends JpaRepository<SimplifiedGenre, UUID> {
}