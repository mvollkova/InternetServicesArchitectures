
import { Routes } from '@angular/router';
import { GenreListComponent } from './genres/genre-list/genre-list.component';

export const routes: Routes = [
  { path: 'genres', component: GenreListComponent },

  { path: '', redirectTo: '/genres', pathMatch: 'full' },
];