import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, mergeMap, Observable, of, take} from 'rxjs';
import { Article, Comment } from "../interfaces/article.interface";
import {tap, switchMap} from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { SessionService } from '../services/session.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl: string = environment.apiUrl + '/articles';
  private _articles = new BehaviorSubject<Article[]>([]);
  public articles$ = this._articles.asObservable();

  constructor(private http: HttpClient, private sessionService: SessionService) {
    this.sessionService.subscribedThemes$.pipe(
      mergeMap(themes => {
        const themeIds = themes.map(theme => theme.id);
        return this.getArticlesForThemes(themeIds);
      })
    ).subscribe();
  }

  getArticlesForThemes(themeIds: number[]): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl).pipe(
      tap(articles => {
        const filteredArticles = articles.filter(article => themeIds.includes(article.themeId));
        this._articles.next(filteredArticles);
      })
    );
  }

  sortArticles(sortBy: string, direction: string = 'desc'): void {
    if (this._articles.value) {
      switch (sortBy) {
        case 'date':
          this._articles.value.sort((a: Article, b: Article) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime());
          break;
        case 'title':
          this._articles.value.sort((a: Article, b: Article) => b.title.localeCompare(a.title));
          break;
      }

      if (direction === 'asc') {
        this._articles.value.reverse();
      }
    }
  }

  getArticle(id: number): Observable<Article> {
    const article = this._articles.value.find(article => article.id === id);
    if (article) {
      return of(article);
    } else {
      return this.http.get<Article>(`${this.apiUrl}/${id}`).pipe(
        tap(fetchedArticle => {
          this._articles.next([...this._articles.value, fetchedArticle]);
        })
      );
    }
  }

  createArticle(article: Pick<Article, 'title' | 'content' | 'themeId'>): Observable<Article> {
    return this.sessionService.user$.pipe(
      take(1),
      switchMap(currentUser => {
        if (!currentUser) {
          throw new Error('Il n\'y a pas d\'utilisateur connecté actuellement');
        }

        const completeArticleData: Omit<Article, 'id' | 'themeTitle' | 'commentIds'> = {
          title: article.title,
          content: article.content,
          name: currentUser.name,
          userId: currentUser.id,
          themeId: article.themeId,
          created_at: new Date(),
          updated_at: new Date(),
        };

        return this.http.post<Article>(`${this.apiUrl}`, completeArticleData);
      })
    );
  }

  getComments(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/${articleId}/comments`);
  }

    createComment(comment: Pick<Comment, 'articleId' | 'content'>): Observable<Comment> {
    return this.sessionService.user$.pipe(
      take(1),
      switchMap(currentUser => {
        if (!currentUser) {
          throw new Error('Il n\'y a pas d\'utilisateur connecté actuellement');
        }

        const completeCommentData: Omit<Comment, 'id'> = {
          content: comment.content,
          userId: currentUser.id,
          name: currentUser.name,
          articleId: comment.articleId,
          created_at: new Date(),
          updated_at: new Date(),
        };

        return this.http.post<Comment>(`${this.apiUrl}/${comment.articleId}/comments`, completeCommentData);
      })
    );
    }
}
