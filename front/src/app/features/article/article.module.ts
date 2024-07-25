import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ArticleRoutingModule } from './article-routing.module';
import { IndexComponent } from './index/index.component';
import {CoreModule} from "../../core/core.module";
import {ArticleService} from "./services/article.service";
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleFormComponent } from './article-form/article-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../../shared/shared.module";
import { ArticleComponent } from './article/article.component';


@NgModule({
  declarations: [
    IndexComponent,
    ArticleListComponent,
    ArticleFormComponent,
    ArticleComponent
  ],
  imports: [
    CommonModule,
    ArticleRoutingModule,
    CoreModule,
    ReactiveFormsModule,
    SharedModule
  ],
  providers: [ArticleService]
})
export class ArticleModule { }
