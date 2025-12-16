
@Injectable({
  providedIn: 'root' 
})
export class GenreService {
  private apiUrl = 'http://localhost:8081/genres'; 

  constructor(private http: HttpClient) { }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiUrl);
  }

  deleteGenre(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  
}