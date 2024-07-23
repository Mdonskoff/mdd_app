import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Login} from "../Interfaces/login.interface";
import {Observable} from "rxjs";

@Injectable()
export class LoginService {

  constructor(private http: HttpClient){}

  private apiUrl = 'http://localhost:8080/api/auth'

  login(login: Login): Observable<{data : {token : string}}> {
    return this.http.post<{data : {token : string}}>(`${this.apiUrl}/login`, login);
  }
}
