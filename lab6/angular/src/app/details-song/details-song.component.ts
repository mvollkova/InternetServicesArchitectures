import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { SongService, SongReadDto } from '../song.service';

@Component({
  selector: 'app-details-song',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './details-song.component.html',
  styleUrl: './details-song.component.css',
})
export class DetailsSongComponent implements OnInit {
  genreId!: string;
  songId!: string;
  song: SongReadDto | null = null;
  loading = false;
  error: string | null = null;

  constructor(private route: ActivatedRoute, private songs: SongService) {}

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
        this.song = s;
        this.loading = false;
      },
      error: (e) => {
        this.loading = false;
        this.error = 'Nie udało się pobrać szczegółów piosenki.';
        console.error(e);
      },
    });
  }
}
