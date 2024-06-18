import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Register} from "../Interfaces/register.interface";
import {Observable} from "rxjs";

@Injectable()
export class RegisterService {

  constructor(private http : HttpClient) { }
  private apiUrl = "http://127.0.0.1:8080/api/auth/register"

  register(user: Register): Observable<void> {
    return this.http.post<void>(this.apiUrl, user)
  }
}
