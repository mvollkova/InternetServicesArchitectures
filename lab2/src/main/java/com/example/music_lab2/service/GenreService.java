package com.example.music_lab2.service;
import com.example.music_lab2.init.DataInitializer;
import com.example.music_lab2.model.Genre;
import com.example.music_lab2.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GenreService {

    private final GenreRepository repo;

    public GenreService(GenreRepository repo) {
        this.repo = repo;
    }

    public List<Genre> findAll() {
        return repo.findAll();
    }

    public Optional<Genre> findById(UUID id) {
        return repo.findById(id);
    }

    @Transactional
    public Genre save(Genre genre) {
        return repo.save(genre);
    }

    @Transactional
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}
