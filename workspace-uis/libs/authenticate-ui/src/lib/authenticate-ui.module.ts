import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthenticatedUserModule } from '@workspace-uis/authenticated-user';
import { RegisterFormComponent } from './register-form/register-form.component';
import {FlexLayoutModule} from "@angular/flex-layout";

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    AuthenticatedUserModule,
    FlexLayoutModule
  ],
  declarations: [LoginFormComponent, RegisterFormComponent],
  exports: [LoginFormComponent, RegisterFormComponent],
})
export class AuthenticateUiModule {}
