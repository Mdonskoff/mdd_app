import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ArticleRoutingModule } from './article-routing.module';
import { IndexComponent } from './index/index.component';
import {CoreModule} from "../../core/core.module";


@NgModule({
  declarations: [
    IndexComponent
  ],
  imports: [
    CommonModule,
    ArticleRoutingModule,
    CoreModule
  ]
})
export class ArticleModule { }
