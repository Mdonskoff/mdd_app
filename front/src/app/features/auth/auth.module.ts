import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { RegisterComponent } from './register/register.component';
import {ReactiveFormsModule} from "@angular/forms";
import {RegisterService} from "./services/register.service";
import {HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import {CoreModule} from "../../core/core.module";

@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent
  ],
    imports: [
        CommonModule,
        CoreModule,
        AuthRoutingModule,
        HttpClientModule,
        ReactiveFormsModule

    ],
  providers : [RegisterService]
})
export class AuthModule { }
