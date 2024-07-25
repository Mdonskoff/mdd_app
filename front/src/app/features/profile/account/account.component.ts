import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../../shared/interfaces/user.interface";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../shared/services/user.service";
import {AuthService} from "../../../shared/services/auth.service";
import {Router} from "@angular/router";
import {mergeMap, Subscription, tap} from "rxjs";
import {Topic} from "../../../shared/interfaces/topic.interface";
import {isPasswordValid} from "../../../shared/validators/password.validators";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit, OnDestroy {

  auth$!: Subscription;
  user$!: Subscription;
  unSubTopics$!: Subscription;
  topics!: Topic[];
  user!: User;
  accountFormGroup = new FormGroup({});
  pseudo! : FormControl;
  email! : FormControl;
  password! : FormControl;
  confirmPassword! : FormControl;
  errorMessage: string = "";

  isError: boolean = false;
  successMsg: string = "";

  accountForm: FormGroup = new FormGroup({});

  constructor(
    private authService: AuthService,
    private userService : UserService,
    private router: Router,
    private fb: FormBuilder
  ){}

  ngOnInit(): void {
    this.user$ = this.userService.getMe().pipe(
      tap(result => {
        this.user = result.data.me;
        /*this.pseudo = new FormControl(this.user.pseudo, Validators.required);
        this.email = new FormControl(this.user.email, [Validators.required, Validators.email]);
        this.password = new FormControl("");
        //this.password = new FormControl(this.accountFormGroup, [Validators.required, Validators.minLength(8),
          //isPasswordValid(/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$/)])
        this.confirmPassword = new FormControl("");
        this.accountFormGroup.addControl("pseudo", this.pseudo);
        this.accountFormGroup.addControl("email", this.email);
        this.accountFormGroup.addControl("password", this.password);
        this.accountFormGroup.addControl("confirmPassword", this.confirmPassword);

         */
        this.topics = this.user.topicsList!
        this.accountForm = new FormGroup ({
          pseudo: new FormControl(""),
          email: new FormControl(""),
          password: new FormControl(""),
          confirmPassword: new FormControl("")
        })
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.user$.unsubscribe();
    if (this.auth$) {
      this.auth$.unsubscribe();
    }
    if (this.unSubTopics$) {
      this.unSubTopics$.unsubscribe();
    }
  }

  logout(): void{
    this.auth$ = this.authService.logout().subscribe();
    this.router.navigate(['/']);
  }

  onSubmit(): void {
    if (this.accountFormGroup.status === "INVALID") {
      this.showError()
      return;
    }

    this.userService.updateMe(
      this.pseudo.value,
      this.email.value,
      this.password.value
    ).pipe(
      mergeMap((result) => {
        this.authService.setToken(result.data.token);
        return this.userService.getMe();
      })
    ).subscribe(
      {
        next : result => { this.user = result.data.me;
          this.showSuccessMsg();
        },
        error : err => this.showErrorMsg(err)
      }
    )
  }

  showError(): void {
    this.isError = true
    this.errorMessage = "Erreur : "
    const controls = this.accountFormGroup.controls
    /*
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
     */
  }

  showSuccessMsg(): void {
    this.successMsg = "Utilisateur mis à jour";
    this.errorMessage = "";
  }
  showErrorMsg(err: { error: { message: string; }; }) {
    this.successMsg = "";
    this.errorMessage = err.error.message;
  }

  unSubscribe(id : string) : void {
    this.unSubTopics$ = this.userService.unSubscribe(id).pipe(
      mergeMap(() => {
        this.removeTopic(id);
        return this.userService.getMe();
      })
    ).subscribe();
  }

  removeTopic(id : string) {
    this.topics = this.topics.filter(topic => topic.id !== id);
  }

}
