import {Component, OnDestroy, OnInit} from '@angular/core';
import {Theme} from "../../interfaces/theme.interface";
import {ThemeService} from "../../services/theme.service";
import {SessionService} from "../../services/session.service";
import {Subscription} from "rxjs";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit, OnDestroy {
  public themes: Theme[] = [];
  public subscribedThemeIds: number[] = [];
  private themesSubscription: Subscription | null = null;
  private userServiceSubscription: Subscription | null = null;

  constructor(private themeService: ThemeService,
              private sessionService: SessionService,
              private userService: UserService) {}

  ngOnInit(): void {
    this.themesSubscription = this.themeService.getThemes().subscribe(allThemes => {
      this.themes = allThemes;
    });

    this.sessionService.subscribedThemes$.subscribe(themes => {
      this.subscribedThemeIds = themes.map(theme => theme.id);
    });
  }

  public isSubscribed(themeId: number): boolean {
    return this.subscribedThemeIds.includes(themeId);
  }

  public onSubscribe(themeId: number): void {
    this.userServiceSubscription = this.userService.subscribeTheme(themeId).subscribe((updatedUser) => {
      this.sessionService.updateUser(updatedUser);
    });
  }

  ngOnDestroy(): void {
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe();
    }
    if (this.userServiceSubscription) {
      this.userServiceSubscription.unsubscribe();
    }
  }
}
