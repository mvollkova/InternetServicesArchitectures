package com.example.music_lab2.repository;

import com.example.music_lab2.model.Song;
import com.example.music_lab2.model.SimplifiedGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, UUID> {

    List<Song> findByGenre(SimplifiedGenre genre);

    List<Song> findByGenreId(UUID genreId);

    List<Song> findAllByGenre_Id(UUID genreId);

    void deleteByGenreId(UUID genreId);

}