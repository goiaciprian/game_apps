import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { MatFormFieldModule, } from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {ReactiveFormsModule} from "@angular/forms";
import {AuthenticatedUserModule} from "@workspace-uis/authenticated-user";

@NgModule({
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule, AuthenticatedUserModule],
  declarations: [
    LoginFormComponent,
  ],
  exports: [
    LoginFormComponent
  ],
})
export class AuthenticateUiModule {}
