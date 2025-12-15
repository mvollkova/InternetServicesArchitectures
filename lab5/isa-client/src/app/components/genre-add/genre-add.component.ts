import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { GenreService } from '../../services/genre.service';

@Component({
  selector: 'app-genre-add',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './genre-add.component.html',
  styleUrls: ['./genre-add.component.css']
})
export class GenreAddComponent implements OnInit {
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

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.genreForm.valid) {
      this.genreService.createGenre(this.genreForm.value)
      .subscribe({
        next: () => {
          this.genreService.notifyGenresUpdated();
          this.router.navigate(['/genres']); 
        },
        error: (err) => {
          console.error('Error creating genre:', err);
        }
      });
    }
  }

  get name() {
    return this.genreForm.get('name');
  }
}