import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {firstValueFrom, take} from 'rxjs';
import { map } from 'rxjs/operators';
import { SessionService } from '../../../services/session.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard {
  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) { }

  async canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {
    await this.sessionService.initializeUser();
    const isLoggedIn = await firstValueFrom(
      this.sessionService.isLoggedIn$.pipe(
        take(1),
        map(isAuthenticated => {
          if (isAuthenticated) {
            return true;
          } else {
            this.router.navigate(['/login']);
            return false;
          }
        })
      )
    );
    return isLoggedIn;
  }
}
