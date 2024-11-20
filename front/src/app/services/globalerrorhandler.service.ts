import { Injectable, ErrorHandler } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private snackBar: MatSnackBar) { }

  handleError(error: Error | HttpErrorResponse) {
    let message: string;

    if (error instanceof HttpErrorResponse) {
      if (error.status === 401) {
        message = 'Veuillez vous connecter';
      } else {
        message = 'Une erreur s\'est produite : ' + error.message;
      }
    } else {
      message = 'Une erreur s\'est produite : ' + error.message;
    }

    this.snackBar.open(message, 'Fermer', {
      duration: 4000,
      horizontalPosition: 'center',
    });
  }
}
