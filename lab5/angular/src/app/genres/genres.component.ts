import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { GenreService, GenreListDto } from '../genre.service';

@Component({
  selector: 'app-genres',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './genres.component.html',
  styleUrl: './genres.component.css',
})
export class GenresComponent implements OnInit {
  genres: GenreListDto[] = [];
  loading = false;
  error: string | null = null;

  constructor(private genreService: GenreService) {}

  ngOnInit(): void {
    this.loadGenres();
  }

  loadGenres(): void {
    this.loading = true;
    this.error = null;
    this.genreService.getGenres().subscribe({
      next: (data) => {
        this.genres = data;
        this.loading = false;
      },
      error: (e) => {
        this.error = 'Nie udało się pobrać listy gatunków.';
        this.loading = false;
        console.error(e);
      },
    });
  }

  remove(id: string): void {
    if (!confirm('Na pewno usunąć ten gatunek?')) return;
    this.genreService.removeGenre(id).subscribe({
      next: () => this.loadGenres(),
      error: (e) => {
        this.error = 'Nie udało się usunąć gatunku.';
        console.error(e);
      },
    });
  }
}
