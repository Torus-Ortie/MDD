import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from "@angular/forms";

import { User } from '../../interfaces/user.interface';
import { Theme} from "../../interfaces/theme.interface";
import { SessionService } from "../../services/session.service";
import { Subscription } from "rxjs";
import { UserService } from "../../services/user.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit , OnDestroy {
  subscribedThemes: Theme[] = [];
  user: User | null = null;
  private themesSubscription: Subscription | null = null;
  private userSubscription: Subscription | null = null;

  formControls: { [key: string]: FormControl } = {
    username: new FormControl('', [Validators.required, Validators.minLength(4)]),
    email: new FormControl('', [Validators.email, Validators.required]),
  };

  labels: { [key: string]: string } = {
    username: 'Nom d’utilisateur',
    email: 'Adresse e-mail',
  };

  controlNames: { [key: string]: string } = {
    username: 'un nom d’utilisateur avec au moins 4 caractères',
    email: 'une adresse e-mail valide',
  };

  errorMessages: { [key: string]: string } = {
    username: '',
    email: '',
  };

  constructor(private sessionService: SessionService,
              private userService: UserService, private router: Router) {}


  ngOnInit(): void {
    this.themesSubscription = this.sessionService.subscribedThemes$.subscribe(themes => {
      this.subscribedThemes = themes;
    });

    this.userSubscription = this.sessionService.user$.subscribe(user => {
      this.user = user;
      if (this.user) {
        console.log('user', this.user);
        this.formControls['name'].setValue(this.user.name);
        this.formControls['email'].setValue(this.user.email);
      } else {
        this.formControls['name'].setValue('');
        this.formControls['email'].setValue('');
      }
    });
  }

  onBlur(controlName: string) {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  onSubmit() {

    if (this.formControls["username"].valid && this.formControls['email'].valid) {
      if (this.user && this.user.id !== undefined && this.user.id !== null) {
        const updatedUser: User = {
          id: this.user.id,
          name: this.formControls['name'].value,
          email: this.formControls['email'].value,
          password: this.user.password,
          subscribedThemeIds: this.user.subscribedThemeIds
        };
        this.userService.updateUser(updatedUser).subscribe((user) => {
          this.sessionService.updateUser(user);
        });
      } else {
         this.sessionService.logOut();
    }
  }
  }

  onLogout(){
    this.sessionService.logOut();
    this.router.navigate(['/login']).then(
      () => {}
    );
  }

  onUnsubscribe(themeId : number){
    this.userService.unsubscribeTheme(themeId).subscribe((updatedUser) => {
      this.sessionService.updateUser(updatedUser);
    });
  }

  ngOnDestroy(): void {
    if (this.themesSubscription) {
      this.themesSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }
}
