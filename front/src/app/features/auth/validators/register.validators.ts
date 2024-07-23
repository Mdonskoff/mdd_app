import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function isPasswordValid(regPassword : RegExp): ValidatorFn {
  return (control:AbstractControl): ValidationErrors | null => {

    const isValid = regPassword.test(control.value);
    return isValid ? null : {checkPassword : {value: control.value}}
  }
}
