package com.example.music_lab2.controller;

import com.example.music_lab2.dto.GenreEventDto;
import com.example.music_lab2.model.SimplifiedGenre;
import com.example.music_lab2.service.SimplifiedGenreService;
import com.example.music_lab2.service.SongService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class GenreEventListenerController {

    private final SimplifiedGenreService simplifiedGenreService;
    private final SongService songService;

    public GenreEventListenerController(SimplifiedGenreService simplifiedGenreService, SongService songService) {
        this.simplifiedGenreService = simplifiedGenreService;
        this.songService = songService;
    }

    @PostMapping("/genre-created") // POST http://localhost:8082/api/events/genre-created
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleGenreCreated(@RequestBody GenreEventDto eventDto) {
        System.out.println("Event received: GENRE CREATED - " + eventDto.getName());
        SimplifiedGenre newStub = new SimplifiedGenre(eventDto.getId(), eventDto.getName());
        simplifiedGenreService.save(newStub);
    }

    @DeleteMapping("/genre-deleted/{id}") // DELETE http://localhost:8082/api/events/genre-deleted/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleGenreDeleted(@PathVariable UUID id) {
        System.out.println("Event received: GENRE DELETED for ID - " + id);

        songService.deleteByGenreId(id);

        simplifiedGenreService.delete(id);
    }
}