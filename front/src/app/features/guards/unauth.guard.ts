import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { ArticleService } from "../../services/article.service";

@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private articleService: ArticleService,
  ) {
  }

  public canActivate(): boolean {
    if (this.articleService.isLogged) {
      this.router.navigate(['rentals']);
      return false;
    }
    return true;
  }
}