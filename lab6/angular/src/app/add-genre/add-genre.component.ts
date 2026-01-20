import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { GenreService } from '../genre.service';

@Component({
  selector: 'app-add-genre',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './add-genre.component.html',
  styleUrl: './add-genre.component.css',
})
export class AddGenreComponent {
  error: string | null = null;
  saving = false;

  form = new FormGroup({
    name: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

  constructor(private genres: GenreService, private router: Router) {}

  submit(): void {
    this.error = null;
    if (this.form.invalid) return;
    this.saving = true;
    this.genres.addGenre(this.form.getRawValue()).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigateByUrl('/genres');
      },
      error: (e) => {
        this.saving = false;
        this.error = 'Nie udało się dodać gatunku.';
        console.error(e);
      },
    });
  }
}
