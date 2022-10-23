import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Component, OnInit} from "@angular/core";
import {AuthenticatedUserDataAccessFacade} from "@workspace-uis/authenticated-user";

@Component({
  selector: 'workspace-uis-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent implements OnInit {
  formGroup: FormGroup;

  constructor(private fb: FormBuilder, private authFacade: AuthenticatedUserDataAccessFacade) {
    this.formGroup = fb.group({
      email: ['', [Validators.email, Validators.required]],
      password: ['', [Validators.required]]
    })
  }

  submitRegister() {
    this.authFacade.registerUser({
      username: this.formGroup.value['email'],
      password: this.formGroup.value['password']
    })
  }

  ngOnInit(): void {}
}
