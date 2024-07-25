import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TopicsComponent} from "./topics/topics.component";
import {authGuard} from "../../shared/auth.guard";

const routes: Routes = [
  {path: '', component: TopicsComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TopicRoutingModule { }
