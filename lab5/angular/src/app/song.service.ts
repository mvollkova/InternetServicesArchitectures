import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SongGenreDto {
  id: string;
  name: string;
}

export interface SongListDto {
  id: string;
  title: string;
  artist: string;
  durationSeconds: number;
  genre: SongGenreDto;
}

export interface SongReadDto extends SongListDto {}

export interface SongCreateUpdateDto {
  title: string;
  artist: string;
  durationSeconds: number;
}

@Injectable({
  providedIn: 'root',
})
export class SongService {
  private songsUrl = 'http://localhost:8080/api/songs';
  private genresUrl = 'http://localhost:8080/api/genres';

  constructor(private http: HttpClient) {}

  getSongs(): Observable<SongListDto[]> {
    return this.http.get<SongListDto[]>(`${this.songsUrl}`);
  }

  getSong(id: string): Observable<SongReadDto> {
    return this.http.get<SongReadDto>(`${this.songsUrl}/${id}`);
  }

  // UWAGA: w Twoim lab4 gateway ma trasę /api/genres/{uuid}/songs -> SongService.
  // Jeśli endpoint jest zaimplementowany w backendzie, to ta metoda zadziała.
  // Jeśli nie, w DetailsGenreComponent i tak filtrujemy po polu song.genre.id.
  getSongsByGenre(genreId: string): Observable<SongListDto[]> {
    return this.http.get<SongListDto[]>(`${this.genresUrl}/${genreId}/songs`);
  }

  addSong(dto: SongCreateUpdateDto): Observable<SongReadDto> {
    return this.http.post<SongReadDto>(`${this.songsUrl}`, dto);
  }

  addSongToGenre(genreId: string, dto: SongCreateUpdateDto): Observable<SongReadDto> {
    return this.http.post<SongReadDto>(`${this.genresUrl}/${genreId}/songs`, dto);
  }

  updateSong(id: string, dto: SongCreateUpdateDto): Observable<SongReadDto> {
    return this.http.put<SongReadDto>(`${this.songsUrl}/${id}`, dto);
  }

  removeSong(id: string): Observable<void> {
    return this.http.delete<void>(`${this.songsUrl}/${id}`);
  }
}
