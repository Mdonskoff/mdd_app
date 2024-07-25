import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticleItem} from "../interfaces/article-item.interface";

@Injectable()
export class ArticleService {

  constructor(private http: HttpClient){}

  private apiUrl = 'http://localhost:8080/api';

  create(articleItem: ArticleItem): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/articles`, articleItem);
  }

  getArticleById(idArticle: string): Observable<{data :{article :ArticleItem}}>{
    return this.http.get<{data :{article :ArticleItem}}>(`${this.apiUrl}/articles/${idArticle}`);
  }

}
