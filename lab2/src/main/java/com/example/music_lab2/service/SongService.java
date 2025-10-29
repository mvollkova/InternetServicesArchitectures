package com.example.music_lab2.service;
import com.example.music_lab2.init.DataInitializer;
import com.example.music_lab2.model.Genre;
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

    public List<Song> findByGenre(Genre genre) {
        return repo.findByGenre(genre);
    }

    public List<Song> findByGenreId(UUID genreId) {
        return repo.findByGenreId(genreId);
    }

    @Transactional
    public Song save(Song song) {
        return repo.save(song);
    }

    @Transactional
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}
