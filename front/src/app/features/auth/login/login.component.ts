import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../../interfaces/user.interface';
import { SessionService } from '../../../services/session.service';
import { AuthSuccess } from '../../../interfaces/authSuccess.interface';
import { LoginRequest } from '../../../interfaces/loginRequest.interface'; 
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public hide = true;
  public onError = false;
  public form!: FormGroup<{ email: FormControl<string | null>; password: FormControl<string | null>; }>;
  
  constructor(
    private authService: AuthService, 
    private fb: FormBuilder, 
    private router: Router,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.min(3)]]
    });
  }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/rentals'])
        });
        this.router.navigate(['/rentals'])
      },
      error => this.onError = true
    );
  }
}