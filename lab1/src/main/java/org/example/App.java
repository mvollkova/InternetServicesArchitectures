package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class App {

    public static void main(String[] args) {


        System.out.println("=================================================");
        System.out.println("--- TASK 2 ---");

        Genre modernRock = Genre.builder().name("Modern Rock").popularityScore(88).build();
        Genre altPop = Genre.builder().name("Alternative Pop").popularityScore(95).build();
        Genre rAndBSoul = Genre.builder().name("R&B/Soul").popularityScore(72).build();

        List<Song> rockSongs = Arrays.asList(
                Song.builder().title("Bones").artist("Imagine Dragons").durationSeconds(170).build(),
                Song.builder().title("Heat Waves").artist("Glass Animals").durationSeconds(239).build(),
                Song.builder().title("Smells Like Teen Spirit").artist("Nirvana").durationSeconds(301).build() // Классика, но в "Modern Rock"
        );

        List<Song> popSongs = Arrays.asList(
                Song.builder().title("As It Was").artist("Harry Styles").durationSeconds(167).build(),
                Song.builder().title("Bad Guy").artist("Billie Eilish").durationSeconds(194).build()
        );

        List<Song> jazzSongs = Arrays.asList(
                Song.builder().title("Cruel Summer").artist("Taylor Swift").durationSeconds(211).build(),
                Song.builder().title("Blinding Lights").artist("The Weeknd").durationSeconds(200).build()
        );

        rockSongs.forEach(s -> s.setGenre(modernRock));
        popSongs.forEach(s -> s.setGenre(altPop));
        jazzSongs.forEach(s -> s.setGenre(rAndBSoul));

        // Genre -> Song
        modernRock.setSongs(rockSongs);
        altPop.setSongs(popSongs);
        rAndBSoul.setSongs(jazzSongs);

        List<Genre> allGenres = Arrays.asList(modernRock, altPop, rAndBSoul);


        System.out.println("genre and songs in original order:");
        allGenres.forEach(g -> {
            System.out.println("-> Genre: " + g);
            g.getSongs().forEach(s -> {
                System.out.println("   -> Song: " + s);
            });
        });
        System.out.println("\n=================================================");
        System.out.println("--- TASK 3---");

        Set<Song> allSongsSet = allGenres.stream()
                .flatMap(g -> g.getSongs().stream())
                .collect(Collectors.toSet());

        System.out.println("all unique songs collected in a Set (order is not guaranteed):");
        allSongsSet.forEach(System.out::println);
        System.out.println("\n=================================================");
        System.out.println("--- TASK 4---");
        long startTimeSeq = System.nanoTime();// start time
        allSongsSet.stream()
                .filter(song -> song.getDurationSeconds() > 300)
                .sorted()
                .forEach(s -> System.out.println("   SEQUENTIAL " + s));

        long endTimeSeq = System.nanoTime(); // time end
        long durationSeq = (endTimeSeq - startTimeSeq) / 1000000; // to milliseconds

        System.out.println("\nsequential stream execution time: " + durationSeq + " мс");



        System.out.println("\n=================================================");
        System.out.println("--- TASK 5 ---");

        List<SongDto> songDtoList = allSongsSet.stream()
                // song to songDto
                .map(song -> SongDto.builder()
                        .title(song.getTitle())
                        .artist(song.getArtist())
                        .durationSeconds(song.getDurationSeconds())
                        .genreName(song.getGenre().getName())
                        .build())
                .sorted()
                .toList();

        System.out.println(" DTO (SongDto):");
        songDtoList.forEach(System.out::println);

        System.out.println("\n=================================================");
        System.out.println("--- TASK 6---");

        final String FILENAME = "lab1.ser";

        //serial
        try (FileOutputStream fileOut = new FileOutputStream(FILENAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(allGenres);
            System.out.println("genre serialized" + FILENAME);
        } catch (IOException ex) {
            System.err.println("error " + ex.getMessage());
        }

        List<Genre> genresDeserialized = null;
        try (FileInputStream fileIn = new FileInputStream(FILENAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            genresDeserialized = (List<Genre>) in.readObject();

            System.out.println("genre deserialized");
            genresDeserialized.forEach(System.out::println);
        } catch (IOException ex) {
            System.err.println("error in deserealization " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("error in deserealization (ClassNotFound): " + ex.getMessage());
        }

        System.out.println("\n=================================================");
        System.out.println("--- TASK 7 parallelStream ---");

        System.out.println("result of filtering (>300 sec) and sorting performed by parallel thread:");

        long startTime = System.nanoTime(); //time start

        allSongsSet.parallelStream()
                .filter(song -> song.getDurationSeconds() > 300)
                .sorted()
                .forEach(s -> System.out.println("   PARALLEL " + s));

        long endTime = System.nanoTime(); // time end
        long duration = (endTime - startTime) / 1000000; // to milliseconds

        System.out.println("\nparallel stream execution time: " + duration + " ms");


    }
}
