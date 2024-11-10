import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { ArticleService } from "../../services/article.service";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private articleService: ArticleService,
  ) {
  }

  public canActivate(): boolean {
    if (!this.articleService.isLogged) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}