import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {IndexComponent} from "./index/index.component";
import {authGuard} from "../../shared/auth.guard";
import {ArticleFormComponent} from "./article-form/article-form.component";
import {ArticleComponent} from "./article/article.component";

const routes: Routes = [
  {path : 'create', component : ArticleFormComponent, canActivate : [authGuard]},
  {path : ':id', component : ArticleComponent, canActivate : [authGuard]},
  {path : '', component : IndexComponent, canActivate : [authGuard]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticleRoutingModule { }
