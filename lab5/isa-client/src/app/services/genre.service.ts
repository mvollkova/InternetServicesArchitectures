import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Genre {
  id: number;
  name: string; 
}

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private genreApiUrl = '/api-genre/genres'; 

  constructor(private http: HttpClient) { }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.genreApiUrl);
  }

  deleteGenre(id: number): Observable<any> {
    return this.http.delete(`${this.genreApiUrl}/${id}`);
  }
}