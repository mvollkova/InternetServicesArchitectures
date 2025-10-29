package com.example.music_lab2.runner;

import com.example.music_lab2.model.Genre;
import com.example.music_lab2.model.Song;
import com.example.music_lab2.service.GenreService;
import com.example.music_lab2.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final GenreService genreService;
    private final SongService songService;

    public ConsoleRunner(GenreService genreService, SongService songService) {
        this.genreService = genreService;
        this.songService = songService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        printHelp();

        while (true) {
            System.out.print(">>> ");
            String input = reader.readLine();

            if (input == null) break;

            switch (input.trim().toLowerCase()) {
                case "help" -> printHelp();
                case "list genres" -> listGenres();
                case "list songs" -> listSongs();
                case "add song" -> addSong(reader);
                case "delete song" -> deleteSong(reader);
                case "quit", "exit" -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Unknown command. Type 'help'");
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                Commands:
                help           - show this help
                list genres    - list all genres
                list songs     - list all songs
                add song       - add a new song
                delete song    - delete song by id
                quit / exit    - stop application
                """);
    }

    private void listGenres() {
        List<Genre> genres = genreService.findAll();
        if (genres.isEmpty()) {
            System.out.println("\nNo genres found in database!\n");
            return;
        }
        System.out.println("\nGenres:");
        genres.forEach(g -> System.out.println(g.getId() + " -> " + g.getName()));
        System.out.println();
    }

    private void listSongs() {
        List<Song> songs = songService.findAll();

        if (songs.isEmpty()) {
            System.out.println("\nNo songs found in database!\n");
            return;
        }

        System.out.println("\nSongs:");
        for (Song s : songs) {
            String genreName = s.getGenre() != null ? s.getGenre().getName() : "No Genre";
            System.out.println(s.getId() + " -> " + s.getTitle() + " | " + s.getArtist() + " | " + genreName);
        }
        System.out.println();
    }

    private void addSong(BufferedReader reader) throws Exception {
        System.out.print("Title: ");
        String title = reader.readLine();

        System.out.print("Artist: ");
        String artist = reader.readLine();

        System.out.print("Duration (sec): ");
        int duration;
        try {
            duration = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Duration must be a valid number!");
            return;
        }

        if (duration <= 0) {
            System.out.println("Duration must be greater than 0!");
            return;
        }

        List<Genre> genres = genreService.findAll();

        Genre genre;
        if (genres.isEmpty()) {
            System.out.print("No genres found! Enter new genre name: ");
            String genreName = reader.readLine().trim();
            genre = genreService.save(new Genre(genreName));
            System.out.println("Genre created: " + genre.getName());
        } else {
            listGenres();
            System.out.print("Genre id: ");
            try {
                UUID genreId = UUID.fromString(reader.readLine().trim());
                genre = genreService.findById(genreId).orElseThrow();
            } catch (Exception e) {
                System.out.println("Invalid genre id!");
                return;
            }
        }

        Song song = new Song(title, artist, duration, genre);
        songService.save(song);
        System.out.println("Song added!\n");
    }



    private void deleteSong(BufferedReader reader) throws Exception {
        System.out.print("Song id: ");
        UUID id = UUID.fromString(reader.readLine());

        songService.deleteById(id);
        System.out.println("Song deleted (if existed)");
    }
}
