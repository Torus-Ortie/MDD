import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './features/guards/unauth.guard';

export const routes: Routes = [
    {
        path: '',
        canActivate: [UnauthGuard],
        loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
    }  
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
  })
export class AppRoutingModule {}
