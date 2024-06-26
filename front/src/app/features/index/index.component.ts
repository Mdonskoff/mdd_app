import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {

  constructor(private router : Router) { }

  ngOnInit(): void {
  }
  login(): void {
    this.router.navigate(["/login"])
  }
  register(): void {
    this.router.navigate(["/register"])
  }

}
