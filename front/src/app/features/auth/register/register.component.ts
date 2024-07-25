import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {isPasswordValid} from "../../../shared/validators/password.validators";
import {Register} from "../Interfaces/register.interface";
import {RegisterService} from "../services/register.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {

  register$!: Subscription;

  isError = false
  errorMessage = ""

  registerForm! : FormGroup;

  pseudo = new FormControl(this.registerForm, Validators.required)

  email = new FormControl(this.registerForm, [Validators.required, Validators.email])

  password = new FormControl(this.registerForm, [Validators.required, Validators.minLength(8),
  isPasswordValid(/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$/)])


  user : Register = {
    email: "", password: "", pseudo: ""
  }

  constructor(private registerService: RegisterService, private router : Router) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      pseudo: this.pseudo, email: this.email, password: this.password
    })
  }
  ngOnDestroy(): void {
    if(this.register$) {
      this.register$.unsubscribe()
    }
  }
  onSubmit(): void {

    if(this.registerForm.status === "INVALID") {
      this.showError()
      return
    }
    this.user.pseudo = this.pseudo.value
    this.user.email = this.email.value
    this.user.password = this.password.value

    this.register$ = this.registerService.register(this.user).subscribe(
      {
        next : _ => {
          this.errorMessage = ""
          this.isError = false
          this.router.navigate(["/login"])
        },
        error: err => {
          this.errorMessage = "Erreur : L'utilisateur n'est pas créé"
          this.isError = true
        }
      }
    )

  }
  showError(): void {
    this.isError = true
    this.errorMessage = "Erreur : "
    const controls = this.registerForm.controls
    if(controls["pseudo"].errors) {
      this.errorMessage += "Veuillez entrer un pseudo"
    }
    else if(controls["email"].errors) {
      this.errorMessage += "Veuillez entrer un email"
    }
    else if(controls['password'].errors) {
      this.errorMessage += "Le mot de passe doit contenir : <br> \
        - 8 caractères minimum <br>\
        - une minuscule <br>\
        - une majuscule <br>\
        - un chiffre <br> \
        - un caractère spécial (!@#$%^&*-).";
    }
  }
}
