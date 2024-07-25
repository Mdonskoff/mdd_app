import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticleItem} from "../../features/article/interfaces/article-item.interface";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient){
  }

  getArticles(): Observable<{data: {Articles: ArticleItem[]}}> {
    return this.http.get<{data: {Articles: ArticleItem[]}}> (`${this.apiUrl}/articles`)
  }

}
