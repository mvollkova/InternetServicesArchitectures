package com.example.music_lab2.service;

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
    //POST
    @Transactional
    public Genre create(Genre genre) {
        return repo.save(genre);
    }

    // PUT
    @Transactional
    public Genre update(Genre genre) {
        return repo.save(genre);
    }

    // DELETE
    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}