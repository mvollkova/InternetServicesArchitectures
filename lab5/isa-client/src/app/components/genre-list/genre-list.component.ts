import { Component, OnInit } from '@angular/core';
import { GenreService, Genre } from '../../services/genre.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-genre-list',
  standalone: true, 
  imports: [CommonModule, RouterLink], 
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.css']
})
export class GenreListComponent implements OnInit {
  genres: Genre[] = [];

  constructor(private genreService: GenreService) { }

  ngOnInit(): void {
  this.loadGenres(); 

  this.genreService.genresUpdated$.subscribe(() => {
    this.loadGenres();
  });
}

  loadGenres(): void {
    console.log('loadGenres called');
    this.genreService.getGenres().subscribe({
      next: (data: Genre[]) => {
        console.log('genres from API:', data);
        this.genres = data;
      },
      error: (err) => console.error('Error loading genres:', err)
    });
  }
  trackById(index: number, genre: Genre): string {
     return genre.id;
  }

  removeGenre(id: string): void {
    if (confirm('Are you sure?')) {
      this.genreService.deleteGenre(id).subscribe({
        next: () => {
          console.log('Genre deleted successfully.');
        },
        error: (err) => console.error('Error deleting genre:', err)
      });
    }
  }
}