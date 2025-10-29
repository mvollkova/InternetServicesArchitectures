package com.example.music_lab2.init;

import com.example.music_lab2.model.Genre;
import com.example.music_lab2.model.Song;
import com.example.music_lab2.service.GenreService;
import com.example.music_lab2.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GenreService genreService;
    private final SongService songService;

    public DataInitializer(GenreService genreService, SongService songService) {
        this.genreService = genreService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) {
        System.out.println("DataInitializer: Adding default data...");

        if (genreService.findAll().isEmpty()) {
            Genre pop = genreService.save(new Genre("Pop"));
            Genre rock = genreService.save(new Genre("Rock"));

            songService.save(new Song("Bad Romance", "Lady Gaga", 295, pop));
            songService.save(new Song("Bohemian Rhapsody", "Queen", 355, rock));

            System.out.println("Default genres and songs added!\n");
        } else {
            System.out.println("Data already exists, skipping init.\n");
        }
    }
}
