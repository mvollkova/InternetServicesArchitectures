import { Routes } from '@angular/router';
import { GenreListComponent } from './components/genre-list/genre-list.component'; 
import { GenreAddComponent } from './components/genre-add/genre-add.component';
export const routes: Routes = [
  { path: 'genres', component: GenreListComponent },
  { path: 'genres/add', component: GenreAddComponent },

  { path: '', redirectTo: '/genres', pathMatch: 'full' },
];