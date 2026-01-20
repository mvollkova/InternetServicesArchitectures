import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SongService } from '../song.service';

@Component({
  selector: 'app-add-song',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './add-song.component.html',
  styleUrl: './add-song.component.css',
})
export class AddSongComponent {
  genreId: string;
  error: string | null = null;
  saving = false;

  form = new FormGroup({
    title: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    artist: new FormControl('', { nonNullable: true }),
    durationSeconds: new FormControl(0, { nonNullable: true, validators: [Validators.min(0)] }),
  });

  constructor(
    route: ActivatedRoute,
    private songs: SongService,
    private router: Router
  ) {
    this.genreId = route.snapshot.paramMap.get('id') as string;
  }

  submit(): void {
    if (this.form.invalid) return;
    this.error = null;
    this.saving = true;

    this.songs.addSongToGenre(this.genreId, this.form.getRawValue()).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/genres', this.genreId]);
      },
      error: (e) => {
        // fallback: jeśli backend nie obsługuje /api/genres/{id}/songs
        console.warn('addSongToGenre failed, fallback to addSong', e);
        this.songs.addSong(this.form.getRawValue()).subscribe({
          next: () => {
            this.saving = false;
            this.router.navigate(['/genres', this.genreId]);
          },
          error: (e2) => {
            this.saving = false;
            this.error = 'Nie udało się dodać piosenki.';
            console.error(e2);
          },
        });
      },
    });
  }
}
