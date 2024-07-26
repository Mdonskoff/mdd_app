import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../../shared/interfaces/user.interface";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../shared/services/user.service";
import {AuthService} from "../../../shared/services/auth.service";
import {Router} from "@angular/router";
import {mergeMap, Subscription, tap} from "rxjs";
import {Topic} from "../../../shared/interfaces/topic.interface";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {
  auth$!: Subscription;
  user$!: Subscription;
  unSubTopics$!: Subscription;
  topics!: Topic[];
  user!: User;
  profileForm: FormGroup = new FormGroup({});
  pseudo! : FormControl;
  password! : FormControl;
  errorMessage: string = "";
  isError: boolean = false;
  successMessage: string = "";

  constructor(
    private authService: AuthService,
    private userService : UserService,
    private router: Router,
    private fb: FormBuilder
  ){
    this.profileForm = this.fb.group({
      pseudo: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.user$ = this.userService.getMe().pipe(
      tap(result => {
        this.user = result.data.me;
        this.topics = this.user.topicsList!
        this.profileForm.patchValue({
          pseudo: this.user.pseudo,
          email: this.user.email
        });
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
    if (this.profileForm.status === "INVALID") {
      this.showError()
      return;
    }
    if (this.profileForm.controls['password'].value !== this.profileForm.controls['confirmPassword'].value) {
      this.showError('samePassword')
      return;
    }
    if (!this.isPasswordValid(this.profileForm.controls['password'].value)) {
      this.showError('password')
      return
    }
    this.userService.updateMe(
      this.profileForm.controls['pseudo'].value,
      this.profileForm.controls['email'].value,
      this.profileForm.controls['password'].value
    ).pipe(
      mergeMap((result) => {
        this.authService.setToken(result.data.token);
        return this.userService.getMe();
      })
    ).subscribe(
      {
        next : result => { this.user = result.data.me;
          this.showSuccessMsg();
          this.profileForm.patchValue({
            password: "",
            confirmPassword: ""
          });
        },
        error : err => {
          this.showErrorMsg(err.error.data.error)
        }
      }
    )
  }

  isPasswordValid(password : string) : boolean{
    let regex = new RegExp(/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$/)
    return regex.test(password);
  }

  showError(error: string = ""): void {
    this.isError = true
    this.errorMessage = "Erreur : "
    const controls = this.profileForm.controls

    if(controls["pseudo"].errors)
      this.errorMessage += "Veuillez entrer un pseudo"
    else if(controls["email"].errors)
      this.errorMessage += "Veuillez entrer un email"
    else if(error == 'password')
      this.errorMessage += "Le mot de passe doit contenir : <br> \
        - 8 caractères minimum <br>\
        - une minuscule <br>\
        - une majuscule <br>\
        - un chiffre <br> \
        - un caractère spécial (!@#$%^&*-).";
    else if(error == 'samePassword')
      this.errorMessage += "Les mots de passe doivent être identiques";
    else
      this.errorMessage += "Vérifiez les champs";
  }

  showSuccessMsg(): void {
    this.successMessage = "Utilisateur mis à jour";
    this.errorMessage = "";
  }
  showErrorMsg(message: string) {
    this.successMessage = "";
    this.errorMessage = message;
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
