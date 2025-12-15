import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  private genresSubject = new BehaviorSubject<any[]>([]);
  genres$ = this.genresSubject.asObservable();

  private apiUrl = 'http://localhost:8081/genres';

  constructor(private http: HttpClient) {}

  loadGenres(): void {
    this.http.get<any[]>(this.apiUrl).subscribe(genres => {
      this.genresSubject.next(genres);
    });
  }

  addGenre(genre: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, genre).pipe(
      tap(created => {
        const current = this.genresSubject.value;
        this.genresSubject.next([...current, created]);
      })
    );
  }

  removeGenre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        const filtered = this.genresSubject.value.filter(g => g.id !== id);
        this.genresSubject.next(filtered);
      })
    );
  }

  getGenre(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateGenre(id: number, genre: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, genre).pipe(
      tap(updated => {
        const updatedList = this.genresSubject.value.map(g =>
          g.id === id ? updated : g
        );
        this.genresSubject.next(updatedList);
      })
    );
  }
}
