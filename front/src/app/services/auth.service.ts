import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/auth.interface';
import { AuthSuccess  } from '../interfaces/auth.interface';
import { RegisterRequest } from '../interfaces/auth.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  /**
   * Call the register route
   *
   * @param registerRequest - The request to register a new user
   * @return An observable that contain the token
   * 
   */
  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${environment.apiUrl}/auth/register`, registerRequest);
  }

  /**
   * Call the login route
   *
   * @param loginRequest - The request to log with a user
   * @return An observable that contain the token
   * 
   */
  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${environment.apiUrl}/auth/login`, loginRequest);
  }

}
