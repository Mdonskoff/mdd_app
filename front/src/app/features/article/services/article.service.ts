import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticleItem} from "../interfaces/article-item.interface";

@Injectable()
export class ArticleService {

  constructor(private http: HttpClient){}

  private apiUrl = 'http://localhost:8080/api';

  create(PostItem: ArticleItem): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/articles`, PostItem);
  }

  getArticleById(idArticle: string): Observable<ArticleItem>{
    return this.http.get<ArticleItem>(`${this.apiUrl}/articles/${idArticle}`);
  }

}
