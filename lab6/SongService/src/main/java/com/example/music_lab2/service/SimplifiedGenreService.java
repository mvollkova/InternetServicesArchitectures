package com.example.music_lab2.service;

import com.example.music_lab2.model.SimplifiedGenre;
import com.example.music_lab2.repository.SimplifiedGenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SimplifiedGenreService {

    private final SimplifiedGenreRepository repo;

    public SimplifiedGenreService(SimplifiedGenreRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public SimplifiedGenre save(SimplifiedGenre genre) { return repo.save(genre); }

    @Transactional
    public void delete(UUID id) { repo.deleteById(id); }
}