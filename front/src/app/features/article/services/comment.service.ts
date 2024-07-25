import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Comment} from "../interfaces/comment.interface";

@Injectable()
export class CommentService {

  constructor(private http: HttpClient){}

  private apiUrl = 'http://localhost:8080/api'

  postComment(comment: Comment): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/comments`, comment);
  }
}
