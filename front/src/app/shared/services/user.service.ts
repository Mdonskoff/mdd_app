import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticleItem} from "../../features/article/interfaces/article-item.interface";
import {User} from "../interfaces/user.interface";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient){
  }

  getArticles(): Observable<{data: {Articles: ArticleItem[]}}> {
    return this.http.get<{data: {Articles: ArticleItem[]}}> (`${this.apiUrl}/user/articles`)
  }
  getMe(): Observable<{data: {me: User}}> {
    return this.http.get<{data: {me: User}}> (`${this.apiUrl}/auth/me`)
  }

  subscribe(id: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/user/topic/${id}`, "");
  }

  unSubscribe(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/user/topic/${id}`);
  }

  updateMe(pseudo: string, email: string, password: string): Observable<{data: {token : string}}>{
    const user = {
      pseudo, email, password
    };
    return this.http.put<{data: {token : string}}>(`${this.apiUrl}/user/update`, user);
  }
}
