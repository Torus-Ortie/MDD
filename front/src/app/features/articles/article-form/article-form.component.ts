import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Theme } from '../../../interfaces/theme.interface';
import { ThemeService } from '../../../services/theme.service';
import { ArticleService } from '../../../services/article.service';
import { Article } from '../../../interfaces/article.interface';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrl: './article-form.component.scss'
})
export class ArticleFormComponent implements OnInit, OnDestroy {
  themes: Theme[] = [];
  formControls: { [key: string]: FormControl } = {
    theme: new FormControl('', [Validators.required]),
    title: new FormControl('', [Validators.required]),
    content: new FormControl('', [Validators.required])
  };

  labels: { [key: string]: string } = {
    theme: 'Sélectionner un thème',
    title: 'Titre de l’article',
    content: 'Contenu de l’article'
  };

  controlNames: { [key: string]: string } = {
    theme: 'un thème',
    title: 'un titre',
    content: 'du contenu'
  };

  errorMessages: { [key: string]: string } = {
    theme: '',
    title: '',
    content: ''
  };

  private themeSubscription: Subscription | null = null;
  private articleSubscription: Subscription | null = null;

  constructor(private themeService: ThemeService, private articleService: ArticleService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.themeSubscription = this.themeService.getThemes().subscribe(themes => {
      this.themes = themes;
      console.log("themes:", themes); //TODO remove log
    });
  }

  onBlur(controlName: string):void {
    const control = this.formControls[controlName];
    control.markAsTouched();
    this.errorMessages[controlName] = control.hasError('required') ? `Veuillez saisir ${this.controlNames[controlName]}` : '';
  }
  onSubmit(): void {
    if (this.formControls['theme'].valid && this.formControls['title'].valid && this.formControls['content'].valid) {
      const newArticle: Pick<Article, 'title' | 'content' | 'themeId'> = {
        themeId: this.formControls['theme'].value,
        title: this.formControls['title'].value,
        content: this.formControls['content'].value
      };

      this.articleSubscription = this.articleService.createArticle(newArticle).subscribe({
        next: () => {
          this.snackBar.open('Article créé avec succès', 'Fermer', {
            duration: 3000,
          });
          Object.values(this.formControls).forEach(control => {
            control.reset();
            control.setErrors(null);
          });
        },
        error: error => {
          throw error;
        }
      });
    }
  }

  ngOnDestroy(): void {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
}
