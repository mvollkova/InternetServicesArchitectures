package org.example;

import java.util.Comparator;

public class SongDto implements Comparable<SongDto> {

    private final String title;
    private final String artist;
    private final int durationSeconds;
    private final String genreName;

    private SongDto(String title, String artist, int durationSeconds, String genreName) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.genreName = genreName;
    }

    public static class Builder {
        private String title;
        private String artist;
        private int durationSeconds;
        private String genreName;

        public Builder title(String title) { this.title = title; return this; }
        public Builder artist(String artist) { this.artist = artist; return this; }
        public Builder durationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; return this; }
        public Builder genreName(String genreName) { this.genreName = genreName; return this; }

        public SongDto build() {
            if (title == null || genreName == null) throw new IllegalStateException("Title and Genre Name must be set");
            return new SongDto(this.title, this.artist, this.durationSeconds, this.genreName);
        }
    }
    public static Builder builder() { return new Builder(); }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }
    public String getGenreName() { return genreName; }

    @Override
    public int compareTo(SongDto other) {
        // title -> arrtist -> genreName
        return Comparator.comparing(SongDto::getTitle)
                .thenComparing(SongDto::getArtist)
                .thenComparing(SongDto::getGenreName)
                .compare(this, other);
    }
    @Override
    public String toString() {
        return "SongDto{title='" + title + "', artist='" + artist + "', duration=" + durationSeconds + "s, genreName='" + genreName + "'}";
    }
}