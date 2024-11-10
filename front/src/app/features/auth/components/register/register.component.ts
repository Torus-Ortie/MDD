import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleService } from '../../../../services/article.service';
import { AuthService } from '../../../../services/auth.service';
import { RegisterRequest } from '../../../../interfaces/auth.interface';
import { AuthSuccess } from '../../../../interfaces/auth.interface';
import { User } from '../../../../interfaces/user.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit{

  public onError = false;
  public form!: FormGroup;

  constructor(private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', [Validators.required, Validators.min(3)]],
      password: ['', [Validators.required, Validators.min(3)]]
    });
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.articleService.logIn(user);
          this.router.navigate(['/rentals'])
        });
      },
      error => this.onError = true
    );
  }

}
