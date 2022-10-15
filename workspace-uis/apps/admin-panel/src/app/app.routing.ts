import {Routes} from "@angular/router";
import {LoginRegisterPageComponent} from "./Pages/login-register-page/login-register-page.component";

export const routes: Routes = [
  { path: "login", component: LoginRegisterPageComponent, data: { type: "login" } },
  { path: "register", component: LoginRegisterPageComponent, data: { type: "register" } }
]
