import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { GenreService } from '../../services/genre.service';

@Component({
  selector: 'app-genre-add',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './genre-add.component.html'
})
export class GenreAddComponent {

  genreForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private genreService: GenreService,
    private router: Router
  ) {
    this.genreForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.genreForm.valid) {
      this.genreService.addGenre(this.genreForm.value).subscribe(() => {
        this.router.navigate(['/genres']);
      });
    }
  }
}
