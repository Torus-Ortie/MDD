import { HttpErrorResponse, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { throwError } from 'rxjs';

export const jwtInterceptor = (request: HttpRequest<any>, next: HttpHandlerFn) => {
  if (request.url.includes('/login') || request.url.includes('/register')) {
    return next(request);
  }

  const token = localStorage.getItem('token');

  if (token) {
    const modifiedRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(modifiedRequest);
  } else {
    return throwError(() => new HttpErrorResponse({ status: 401, statusText: 'Veuillez vous connecter' }));
  }
};