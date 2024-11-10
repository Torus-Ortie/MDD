import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';
import { User } from './interfaces/user.interface';
import { ArticleService } from './services/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private router: Router,
    private articleService: ArticleService) {
  }

  public ngOnInit(): void {
    this.autoLog();
  }

  public $isLogged(): Observable<boolean> {
    return this.articleService.$isLogged();
  }

  public logout(): void {
    this.articleService.logOut();
    this.router.navigate([''])
  }

  public autoLog(): void {
    this.authService.me().subscribe(
      (user: User) => {
        this.articleService.logIn(user);
      },
      (_) => {
        this.articleService.logOut();
      }
    )
  }
}
