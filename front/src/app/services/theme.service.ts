import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Theme } from "../interfaces/theme.interface";
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private apiUrl: string = environment.apiUrl + '/themes';
  private themes: Theme[] | null = null;

  constructor(private http: HttpClient) { }

  getThemes(): Observable<Theme[]> {
    if (this.themes) {
      return of(this.themes);
    } else {
      return this.http.get<Theme[]>(this.apiUrl).pipe(
        tap(themes => this.themes = themes)
      );
    }
  }
}
