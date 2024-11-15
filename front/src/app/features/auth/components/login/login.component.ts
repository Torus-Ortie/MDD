import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from '../../../../services/session.service';
import { LoginRequest } from '../../../../interfaces/auth.interface'; 
import { AuthService } from '../../../../services/auth.service';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  public formControls: { [key: string]: FormControl } = {
    emailOrName: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  };

  public labels: { [key: string]: string } = {
    emailOrName: 'E-mail ou nom d’utilisateur',
    password: 'Mot de passe',
  };

  public controlNames: { [key: string]: string } = {
    emailOrName: 'votre e-mail ou nom d’utilisateur',
    password: 'votre mot de passe',
  };

  public errorMessages: { [key: string]: string } = {
    emailOrName: '',
    password: '',
  };

  loginSubscription: Subscription | null = null;

  constructor(private authService: AuthService,
              private sessionService: SessionService, private router: Router) {}

  ngOnInit(): void {}

  login(): void {
    const loginRequest: LoginRequest = {
      emailOrName: this.formControls['emailOrName'].value,
      password: this.formControls['password'].value,
    };

    this.loginSubscription = this.authService.login(loginRequest)
      .subscribe({
        next: (data) => {
          this.sessionService.logIn(data.token);
          this.router.navigate(['/articles']).then(
            () => {
            }
          );
        },
        error: error => {
          throw error;
        }
      });
  }

  onBlur(controlName: string): void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }

  onSubmit(): void {
    if (this.formControls['emailOrName'].valid && this.formControls['password'].valid) {
      this.login();
    }
  }

  ngOnDestroy(): void {
    if(this.loginSubscription){
      this.loginSubscription.unsubscribe();
    }
  }
}