package org.example;

import java.io.Serializable;
import java.util.*;

public class Genre implements Comparable<Genre>, Serializable {

    private final String name;
    private final int popularityScore;

    private List<Song> songs;

    private Genre(String name, int popularityScore) {
        this.name = name;
        this.popularityScore = popularityScore;
        this.songs = new ArrayList<>();
    }

    public static class Builder {
        private String name;
        private int popularityScore = 0;

        public Builder name(String name) { this.name = name; return this; }
        public Builder popularityScore(int ps) { this.popularityScore = ps; return this; }

        public Genre build() {
            if (name == null) throw new IllegalStateException("Name is required.");
            return new Genre(this.name, this.popularityScore);
        }
    }

    public static Builder builder() { return new Builder(); }

    public String getName() { return name; }
    public int getPopularityScore() { return popularityScore; }
    public List<Song> getSongs() { return songs; }

    public void setSongs(List<Song> songs) { this.songs = songs; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return popularityScore == genre.popularityScore && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, popularityScore);
    }

    @Override
    public int compareTo(Genre other) {
        return Comparator.comparing(Genre::getName)
                .thenComparingInt(Genre::getPopularityScore)
                .compare(this, other);
    }
    public String toString() {
        return "Genre{name='" + name + "', popularity score=" + popularityScore + '}';
    }
}