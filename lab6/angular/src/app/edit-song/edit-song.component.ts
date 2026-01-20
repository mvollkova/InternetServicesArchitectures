import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SongService } from '../song.service';

@Component({
  selector: 'app-edit-song',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './edit-song.component.html',
  styleUrl: './edit-song.component.css',
})
export class EditSongComponent implements OnInit {
  genreId!: string;
  songId!: string;
  loading = false;
  saving = false;
  error: string | null = null;

  form = new FormGroup({
    title: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    artist: new FormControl('', { nonNullable: true }),
    durationSeconds: new FormControl(0, { nonNullable: true, validators: [Validators.min(0)] }),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private songs: SongService
  ) {}

  ngOnInit(): void {
    this.genreId = this.route.snapshot.paramMap.get('id') as string;
    this.songId = this.route.snapshot.paramMap.get('songId') as string;
    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;
    this.songs.getSong(this.songId).subscribe({
      next: (s) => {
        this.form.patchValue({
          title: s.title,
          artist: s.artist,
          durationSeconds: s.durationSeconds,
        });
        this.loading = false;
      },
      error: (e) => {
        this.loading = false;
        this.error = 'Nie udało się pobrać danych piosenki.';
        console.error(e);
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    this.saving = true;
    this.error = null;
    this.songs.updateSong(this.songId, this.form.getRawValue()).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/genres', this.genreId, 'songs', this.songId]);
      },
      error: (e) => {
        this.saving = false;
        this.error = 'Nie udało się zaktualizować piosenki.';
        console.error(e);
      },
    });
  }
}
