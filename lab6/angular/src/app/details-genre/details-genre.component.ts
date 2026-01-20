import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { GenreService, GenreReadDto } from '../genre.service';
import { SongService, SongListDto } from '../song.service';

@Component({
  selector: 'app-details-genre',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './details-genre.component.html',
  styleUrl: './details-genre.component.css',
})
export class DetailsGenreComponent implements OnInit {
  id!: string;
  genre: GenreReadDto | null = null;
  songs: SongListDto[] = [];
  loadingGenre = false;
  loadingSongs = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private genres: GenreService,
    private songsService: SongService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id') as string;
    this.loadGenre();
    this.loadSongs();
  }

  loadGenre(): void {
    this.loadingGenre = true;
    this.error = null;
    this.genres.getGenre(this.id).subscribe({
      next: (g) => {
        this.genre = g;
        this.loadingGenre = false;
      },
      error: (e) => {
        this.loadingGenre = false;
        this.error = 'Nie udało się pobrać szczegółów gatunku.';
        console.error(e);
      },
    });
  }

  loadSongs(): void {
    this.loadingSongs = true;
    this.error = null;

    this.songsService.getSongsByGenre(this.id).subscribe({
      next: (list) => {
        this.songs = list;
        this.loadingSongs = false;
      },
      error: (e) => {
        // Fallback: jeśli backend nie ma endpointu /api/genres/{id}/songs
        // to pobieramy wszystkie piosenki i filtrujemy po song.genre.id.
        console.warn('getSongsByGenre failed, fallback to getSongs + filter', e);
        this.songsService.getSongs().subscribe({
          next: (all) => {
            this.songs = (all || []).filter((s) => s.genre?.id === this.id);
            this.loadingSongs = false;
          },
          error: (e2) => {
            this.loadingSongs = false;
            this.error = 'Nie udało się pobrać listy piosenek.';
            console.error(e2);
          },
        });
      },
    });
  }

  removeSong(songId: string): void {
    if (!confirm('Na pewno usunąć tę piosenkę?')) return;
    this.songsService.removeSong(songId).subscribe({
      next: () => this.loadSongs(),
      error: (e) => {
        this.error = 'Nie udało się usunąć piosenki.';
        console.error(e);
      },
    });
  }
}
