import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { RegisterRequest } from '../../../../interfaces/auth.interface';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {


  private subscription: Subscription | undefined;

formControls: { [key: string]: FormControl } = {
  name: new FormControl('', [Validators.required, Validators.minLength(4)]),
  email: new FormControl('', [Validators.email, Validators.required]),
  password: new FormControl('', [
    Validators.required,
    Validators.minLength(8),
    Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$'),
  ]),
};

labels: { [key: string]: string } = {
  name: 'Nom d’utilisateur',
  email: 'Adresse e-mail',
  password: 'Mot de passe',
};

controlNames: { [key: string]: string } = {
  name: 'un nom d’utilisateur avec au moins 4 caractères',
  email: 'une adresse e-mail valide',
  password: 'un mot de passe avec au moins 8 caractères, dont 1 lettre majuscule, 1 lettre minuscule, 1 chiffre et 1 caractère spécial',
};

errorMessages: { [key: string]: string } = {
  name: '',
  email: '',
  password: '',
};

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}


  onBlur(controlName: string): void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    if (control.hasError('required')) {
      this.errorMessages[controlName] = `Veuillez saisir ${this.controlNames[controlName]}`;
    } else if (control.hasError('pattern')) {
      this.errorMessages[controlName] = 'The password does not match the required pattern';
    } else {
      this.errorMessages[controlName] = '';
    }
  }

  onSubmit(): void {
    if (this.formControls["name"].valid && this.formControls['email'].valid && this.formControls['password'].valid) {
      const registerRequest: RegisterRequest = {
        name: this.formControls['name'].value,
        email: this.formControls['email'].value,
        password: this.formControls['password'].value,
      };
      this.subscription = this.authService.register(registerRequest).subscribe({
        next: () => {
          this.router.navigate(['/articles']).then(
            () => {}
          );
        },
        error: error => {
          throw error;
        }
      });
    }}

    ngOnDestroy(): void {
      if (this.subscription) {
      this.subscription.unsubscribe();
  }
  }
}
