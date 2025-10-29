package com.example.music_lab2.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

    public Genre() {
        this.id = UUID.randomUUID();
    }

    public Genre(String name) {
        this();
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public void addSong(Song s) {
        songs.add(s);
        s.setGenre(this);
    }

    public void removeSong(Song s) {
        songs.remove(s);
        s.setGenre(null);
    }

    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre other = (Genre) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
