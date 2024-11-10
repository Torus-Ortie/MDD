import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../../../interfaces/user.interface';
import { ArticleService } from '../../../../services/article.service';
import { AuthSuccess } from '../../../../interfaces/auth.interface';
import { LoginRequest } from '../../../../interfaces/auth.interface'; 
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public hide = true;
  public onError = false;

  public form: FormGroup;

  constructor(private authService: AuthService, 
    private fb: FormBuilder, 
    private router: Router,
    private articleService: ArticleService) { }

    ngOnInit(): void {
      this.form = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        name: ['', [Validators.required, Validators.min(3)]],
        password: ['', [Validators.required, Validators.min(3)]]
      });
    }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.articleService.logIn(user);
          this.router.navigate(['/rentals'])
        });
        this.router.navigate(['/rentals'])
      },
      error => this.onError = true
    );
  }
}