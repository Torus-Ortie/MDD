import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './features/auth/guards/unauth.guard';
import { AuthGuard } from './features/auth/guards/auth.guard';
import { MeComponent } from './components/me/me.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { RegisterComponent } from './features/auth/components/register/register.component';
import { ThemesComponent } from './features/themes/themes.component';
import { ArticleListComponent } from './features/articles/article-list/article-list.component';
import { ArticleFormComponent } from './features/articles/article-form/article-form.component';
import { ArticleDetailComponent } from './features/articles/article-detail/article-detail.component';
import { HeaderComponent } from './components/header/header.component';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [UnauthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard] },
  {
    path: '',
    component: HeaderComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'me', component: MeComponent },
      { path: 'themes', component: ThemesComponent },
      { path: 'articles', component: ArticleListComponent },
      { path: 'articles/new', component: ArticleFormComponent },
      { path: 'articles/:id', component: ArticleDetailComponent }
    ]
  },
  { path: '404', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
