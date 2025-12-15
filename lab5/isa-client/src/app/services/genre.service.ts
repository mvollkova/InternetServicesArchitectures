import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';

export interface Genre {
  id: string;
  name: string;
}

@Injectable({ providedIn: 'root' })
export class GenreService {
  private apiUrl = '/api-genre/genres';

  private genresUpdatedSource = new Subject<void>();
  genresUpdated$ = this.genresUpdatedSource.asObservable();

  constructor(private http: HttpClient) {}

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiUrl);
  }

  createGenre(genre: any): Observable<Genre> {
    return this.http.post<Genre>(this.apiUrl, genre).pipe(
      tap(() => this.genresUpdatedSource.next())
    );
  }
  notifyGenresUpdated() {
     this.genresUpdatedSource.next();
  }

  deleteGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => this.genresUpdatedSource.next())
    );
  }
}
