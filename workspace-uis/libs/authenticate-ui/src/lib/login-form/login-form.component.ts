import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticatedUserDataAccessFacade} from "@workspace-uis/authenticated-user";

@Component({
  selector: 'workspace-uis-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {

  formGroup: FormGroup;

  ngOnInit(): void {}

  submit() {
    this.authFacade.loginUser({
      username: this.formGroup.value['username'],
      password: this.formGroup.value['password']
    });
  }

  constructor(private formBuilder: FormBuilder, private authFacade: AuthenticatedUserDataAccessFacade) {
    this.formGroup = this.formBuilder.group({
      username: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required]]
    })
  }
}

