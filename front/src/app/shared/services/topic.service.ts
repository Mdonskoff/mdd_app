import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Topic} from "../interfaces/topic.interface";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient){}

  private apiUrl = 'http://localhost:8080/api';

  getTopics(): Observable<{data: {Topics: Topic[]}}> {
    return this.http.get<{data: {Topics: Topic[]}}>(`${this.apiUrl}/topic`);
  }

}
