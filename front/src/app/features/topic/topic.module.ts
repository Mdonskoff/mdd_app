import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TopicRoutingModule } from './topic-routing.module';
import { TopicsComponent } from './topics/topics.component';
import {CoreModule} from "../../core/core.module";


@NgModule({
  declarations: [
    TopicsComponent
  ],
  imports: [
    CommonModule,
    TopicRoutingModule,
    CoreModule
  ]
})
export class TopicModule { }
