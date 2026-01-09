import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { GenreService } from '../genre.service';

@Component({
  selector: 'app-edit-genre',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './edit-genre.component.html',
  styleUrl: './edit-genre.component.css',
})
export class EditGenreComponent implements OnInit {
  id!: string;
  loading = false;
  saving = false;
  error: string | null = null;

  form = new FormGroup({
    name: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private genres: GenreService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id') as string;
    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;
    this.genres.getGenre(this.id).subscribe({
      next: (g) => {
        this.form.patchValue({ name: g.name });
        this.loading = false;
      },
      error: (e) => {
        this.loading = false;
        this.error = 'Nie udało się pobrać danych gatunku.';
        console.error(e);
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    this.saving = true;
    this.error = null;
    this.genres.updateGenre(this.id, this.form.getRawValue()).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/genres', this.id]);
      },
      error: (e) => {
        this.saving = false;
        this.error = 'Nie udało się zaktualizować gatunku.';
        console.error(e);
      },
    });
  }
}
