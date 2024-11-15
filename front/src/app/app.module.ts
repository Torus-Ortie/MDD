import { NgModule, ErrorHandler, LOCALE_ID } from '@angular/core';

import { MatDivider } from "@angular/material/divider";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatMenuModule} from "@angular/material/menu";
import {MatOption, MatSelect} from "@angular/material/select";

import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { MeComponent } from './components/me/me.component';
import { jwtInterceptor } from './features/auth/interceptors/jwt.interceptor';
import { HomeComponent } from './components/home/home.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { RegisterComponent } from './features/auth/components/register/register.component';
import { ThemesComponent } from './features/themes/themes.component';
import { ArticleDetailComponent } from './features/articles/article-detail/article-detail.component';
import { ArticleFormComponent } from './features/articles/article-form/article-form.component';
import { ArticleListComponent } from './features/articles/article-list/article-list.component';
import { GlobalErrorHandler } from './services/globalerrorhandler.service';
// NgOptimizedImage  MatIcon  MatProgressSpinner  MatNavList

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatToolbarModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatInputModule,
  MatDivider,
  MatMenuModule,
  MatOption,
  MatSelect
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NotFoundComponent,
    MeComponent,
    HomeComponent,
    HeaderComponent,
    SidenavComponent,
    ThemesComponent,
    ArticleDetailComponent,
    ArticleFormComponent,
    ArticleListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule, 
    FlexLayoutModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ...materialModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr'},
    provideHttpClient(withInterceptors([jwtInterceptor])),
    { provide: ErrorHandler, useClass: GlobalErrorHandler }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
