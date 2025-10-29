package org.example;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Song implements Comparable<Song>, Serializable {

    private final String title;
    private final String artist;
    private final int durationSeconds;
    private Genre genre;

    private Song(String title, String artist, int durationSeconds) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    public static class Builder {
        private String title;
        private String artist;
        private int durationSeconds = 0;
        private Genre genre;

        public Builder title(String title) { this.title = title; return this; }
        public Builder artist(String artist) { this.artist = artist; return this; }
        public Builder durationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; return this; }
        public Builder genre(Genre genre) { this.genre = genre; return this; }

        public Song build() {
            if (title == null || artist == null) throw new IllegalStateException("Title and Artist are required.");
            Song song = new Song(this.title, this.artist, this.durationSeconds);
            song.setGenre(this.genre);
            return song;
        }
    }
    public static Builder builder() { return new Builder(); }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }
    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return durationSeconds == song.durationSeconds &&
                Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, artist, durationSeconds);
    }
    @Override
    public int compareTo(Song other) {
        return Comparator.comparing(Song::getTitle)
                .thenComparing(Song::getArtist)
                .compare(this, other);
    }

    @Override
    public String toString() {
        String genreName = genre != null ? genre.getName() : "N/A";
        return "Song{title='" + title + "', artist='" + artist + "', duration=" + durationSeconds + "s, genre='" + genreName + "'}";
    }
}