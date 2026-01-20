package com.example.music_lab2.service;

import com.example.music_lab2.model.SimplifiedGenre; // !!! ИЗМЕНЕНИЕ
import com.example.music_lab2.model.Song;
import com.example.music_lab2.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SongService {

    private final SongRepository repo;

    public SongService(SongRepository repo) {
        this.repo = repo;
    }

    public List<Song> findAll() {
        return repo.findAll();
    }

    public Optional<Song> findById(UUID id) {
        return repo.findById(id);
    }

    public List<Song> findByGenre(SimplifiedGenre genre) {
        return repo.findByGenre(genre);
    }

    public List<Song> findByGenreId(UUID genreId) {
        return repo.findByGenreId(genreId);
    }

    @Transactional
    public void deleteByGenreId(UUID genreId) {
        repo.deleteByGenreId(genreId);
    }

    @Transactional
    public Song create(Song song) {
        return repo.save(song);
    }

    @Transactional
    public Song update(Song song) {
        return repo.save(song);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}