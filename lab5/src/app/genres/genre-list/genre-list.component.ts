
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenreService, Genre } from '../../genre.service';
import { Router, RouterModule } from '@angular/router'; 

@Component({
  selector: 'app-genre-list',
  standalone: true,
  imports: [CommonModule, RouterModule], 
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.css']
})
export class GenreListComponent implements OnInit {
  genres: Genre[] = [];

  constructor(
    private genreService: GenreService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadGenres();
  }

  loadGenres(): void {
    this.genreService.getGenres().subscribe({
      next: (data) => {
        this.genres = data;
        console.log('Жанры успешно загружены:', this.genres);
      },
      error: (err) => {
        console.error('Ошибка загрузки жанров:', err);
      }
    });
  }

  deleteGenre(id: string): void {
    if (confirm(`Вы уверены, что хотите удалить жанр с ID ${id}?`)) {
      this.genreService.deleteGenre(id).subscribe({
        next: () => {
          console.log(`Жанр ${id} успешно удален.`);
          this.genres = this.genres.filter(genre => genre.id !== id);
        },
        error: (err) => {
          console.error('Ошибка при удалении жанра:', err);
          alert('Не удалось удалить жанр. Проверьте консоль.');
        }
      });
    }
  }

  navigateToEdit(id: string): void {
    this.router.navigate(['/genres', id, 'edit']); 
  }
  
  navigateToSongs(id: string): void {
    this.router.navigate(['/genres', id, 'songs']);
  }

  navigateToAdd(): void {
    this.router.navigate(['/genres/add']); 
  }
}