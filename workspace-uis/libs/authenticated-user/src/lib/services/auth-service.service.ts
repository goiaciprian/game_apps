import {Inject, Injectable} from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {LoginRegisterResponseModel} from "../+state/loginregisterresponse.models";
import {RegisterLoginEntity} from "@workspace-uis/authenticated-user";
import {Observable} from "rxjs";
import {ENVIRONMENT_CONFIG, IEnvironmentConfig} from "@workspace-uis/configurations";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _client: HttpClient, @Inject(ENVIRONMENT_CONFIG) private env_config: IEnvironmentConfig ) { }

  logIn(credentials: RegisterLoginEntity): Observable<LoginRegisterResponseModel> {
    console.log(this.env_config);
    return this._client.post<LoginRegisterResponseModel>(`${this.env_config.baseURL}/api/auth/login`, credentials)
  }

  register(credentials: RegisterLoginEntity): Observable<LoginRegisterResponseModel> {
    return this._client.post<LoginRegisterResponseModel>(`${this.env_config.baseURL}/api/auth/register`, credentials)
  }
}
