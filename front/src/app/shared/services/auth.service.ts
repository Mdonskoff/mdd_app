import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticate = false;
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http : HttpClient){}

  getToken(): string|null{
    return localStorage.getItem('jwt');
  }

  setToken(token: string): void{
    localStorage.setItem('jwt', token);
  }

  isLogged(): boolean{
    let token = localStorage.getItem('jwt');
    return token !== null ? true : false;
  }

  removeToken(): void {
    localStorage.removeItem('jwt');
  }

  logout(): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/logout`);
  }

  login(token : string): void {
    this.setToken(token);
  }
}
