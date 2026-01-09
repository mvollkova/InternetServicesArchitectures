import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface GenreListDto {
  id: string;
  name: string;
}

export interface GenreReadDto {
  id: string;
  name: string;
}

export interface GenreCreateUpdateDto {
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class GenreService {
  private apiUrl = 'http://localhost:8080/api/genres';

  constructor(private http: HttpClient) {}

  getGenres(): Observable<GenreListDto[]> {
    return this.http.get<GenreListDto[]>(`${this.apiUrl}`);
  }

  getGenre(id: string): Observable<GenreReadDto> {
    return this.http.get<GenreReadDto>(`${this.apiUrl}/${id}`);
  }

  addGenre(dto: GenreCreateUpdateDto): Observable<GenreReadDto> {
    return this.http.post<GenreReadDto>(`${this.apiUrl}`, dto);
  }

  updateGenre(id: string, dto: GenreCreateUpdateDto): Observable<GenreReadDto> {
    return this.http.put<GenreReadDto>(`${this.apiUrl}/${id}`, dto);
  }

  removeGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
