package com.example.music_lab2.service;

import com.example.music_lab2.dto.GenreEventDto;
import com.example.music_lab2.model.Genre;
import com.example.music_lab2.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenreService {

    private final GenreRepository repo;
    private final WebClient webClient;
    private static final String SONGS_SERVICE_URL = "http://localhost:8082/api/events/";

    public GenreService(GenreRepository repo) {
        this.repo = repo;
        this.webClient = WebClient.create();
    }

    public List<Genre> findAll() { return repo.findAll(); }
    public Optional<Genre> findById(UUID id) { return repo.findById(id); }

    // POST
    @Transactional
    public Genre create(Genre genre) {
        Genre savedGenre = repo.save(genre);
        notifyGenreCreated(savedGenre);
        return savedGenre;
    }

    public Genre update(Genre genre) { return repo.save(genre); }

    // DELETE
    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
        notifyGenreDeleted(id);
    }


    private void notifyGenreCreated(Genre genre) {
        GenreEventDto dto = new GenreEventDto(genre.getId(), genre.getName());

        webClient.post()
                .uri(SONGS_SERVICE_URL + "genre-created")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        response -> System.out.println("Song Service notified: Genre Created"),
                        error -> System.err.println("Error notifying Song Service (Created): " + error.getMessage())
                );
    }

    private void notifyGenreDeleted(UUID id) {
        webClient.delete()
                .uri(SONGS_SERVICE_URL + "genre-deleted/" + id)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        response -> System.out.println("Song Service notified: Genre Deleted"),
                        error -> System.err.println("Error notifying Song Service (Deleted): " + error.getMessage())
                );
    }
}