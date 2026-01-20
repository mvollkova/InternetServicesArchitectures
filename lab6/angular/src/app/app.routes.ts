import { Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GenresComponent } from './genres/genres.component';
import { AddGenreComponent } from './add-genre/add-genre.component';
import { EditGenreComponent } from './edit-genre/edit-genre.component';
import { DetailsGenreComponent } from './details-genre/details-genre.component';

import { AddSongComponent } from './add-song/add-song.component';
import { EditSongComponent } from './edit-song/edit-song.component';
import { DetailsSongComponent } from './details-song/details-song.component';

export const routes: Routes = [
  { path: '', redirectTo: '/genres', pathMatch: 'full' },

  // 1) lista genre + delete
  { component: GenresComponent, path: 'genres', pathMatch: 'full' },

  // 2) add genre
  { component: AddGenreComponent, path: 'genres/new', pathMatch: 'full' },

  // 3) edit genre (pre-filled)
  { component: EditGenreComponent, path: 'genres/:id/edit', pathMatch: 'full' },

  // 4) genre details + lista songów + delete song
  { component: DetailsGenreComponent, path: 'genres/:id', pathMatch: 'full' },

  // 5) add song to genre
  { component: AddSongComponent, path: 'genres/:id/songs/new', pathMatch: 'full' },

  // 6) edit song (pre-filled)
  { component: EditSongComponent, path: 'genres/:id/songs/:songId/edit', pathMatch: 'full' },

  // 7) song details
  { component: DetailsSongComponent, path: 'genres/:id/songs/:songId', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
