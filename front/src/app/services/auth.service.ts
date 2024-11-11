import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/auth.interface';
import { AuthSuccess  } from '../interfaces/auth.interface';
import { RegisterRequest } from '../interfaces/auth.interface';
import { User } from '../interfaces/user.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    console.log(registerRequest);
    return this.httpClient.post<AuthSuccess>(`${environment.apiUrl}/auth/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${environment.apiUrl}/auth/login`, loginRequest);
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${environment.apiUrl}/auth/me`);
  }
}
