import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { GenreService } from '../../services/genre.service';

@Component({
  selector: 'app-genre-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './genre-list.component.html'
})
export class GenreListComponent implements OnInit {

  genres$: Observable<any[]>;

  constructor(
    private genreService: GenreService,
    private router: Router
  ) {
    this.genres$ = this.genreService.genres$;
  }

  ngOnInit(): void {
    this.genreService.loadGenres();
  }

  removeGenre(genre: any): void {
    if (genre.id !== undefined) {
      this.genreService.removeGenre(genre.id).subscribe();
    }
  }

  editGenre(id: number | undefined): void {
    if (id !== undefined) {
      this.router.navigate(['/genre/edit', id]);
    }
  }
}
