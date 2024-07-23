import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {Login} from "../Interfaces/login.interface";
import {LoginService} from "../services/login.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../shared/services/auth.service";
import {UserService} from "../../../shared/services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  login$!: Subscription;
  pseudo = new FormControl("");
  password = new FormControl("");
  loginFormGroup = new FormGroup({});
  loginUser! : Login;
  errorMsg = "";

  constructor(
    private loginService: LoginService,
    private router : Router,
    private authService : AuthService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loginFormGroup.addControl('ID', this.pseudo);
    this.loginFormGroup.addControl('password', this.password);
    this.loginUser = {id : "", password: ""};
  }

  ngOnDestroy(): void {
    if (this.login$) {
      this.login$.unsubscribe();
    }
  }

  onSubmit(): void{
    if (this.loginFormGroup.status === 'INVALID'){
      this.errorMsg= "erreur : Vérifiez les différents champs";
      return;
    }
    this.loginUser.id = this.pseudo.value!;
    this.loginUser.password = this.password.value!;
    this.login$ = this.loginService.login(this.loginUser).subscribe({
      next : data => {
        this.errorMsg = "";
        this.connect(data)
        },
      error: err => {
        this.errorMsg= err.error.data.error;
      }
    })
  }

  connect(jwt : {data : {token : string}}) : void {
    this.authService.login(jwt.data.token);
    this.router.navigate(["/article"])
  }

}
