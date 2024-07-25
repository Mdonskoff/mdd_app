import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import {ReactiveFormsModule} from "@angular/forms";
import {CoreModule} from "../../core/core.module";
import { AccountComponent } from './account/account.component';


@NgModule({
  declarations: [
    AccountComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    ReactiveFormsModule,
    CoreModule
  ]
})
export class ProfileModule { }
