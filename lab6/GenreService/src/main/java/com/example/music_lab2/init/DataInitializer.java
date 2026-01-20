package com.example.music_lab2.init;

import com.example.music_lab2.model.Genre;
// import com.example.music_lab2.model.Song; // Удалено
import com.example.music_lab2.service.GenreService;
// import com.example.music_lab2.service.SongService; // Удалено
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GenreService genreService;


    public DataInitializer(GenreService genreService ) {
        this.genreService = genreService;

    }

    @Override
    public void run(String... args) {
        System.out.println("DataInitializer: Adding default data...");

        if (genreService.findAll().isEmpty()) {

            Genre pop = genreService.create(new Genre("Pop"));
            Genre rock = genreService.create(new Genre("Rock"));


            System.out.println("Default genres added!\n");
        } else {
            System.out.println("Data already exists, skipping init.\n");
        }
    }
}