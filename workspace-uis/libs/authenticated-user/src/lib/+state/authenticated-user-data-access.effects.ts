import { Injectable } from '@angular/core';
import { createEffect, Actions, ofType } from '@ngrx/effects';

import * as AuthenticatedUserDataAccessActions from './authenticated-user-data-access.actions';
import {map, switchMap} from "rxjs";
import {AuthService} from "../services/auth-service.service";

@Injectable()
export class AuthenticatedUserDataAccessEffects {

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthenticatedUserDataAccessActions.logInUser),
      switchMap(({ loginCredentials }) =>
        this.authService.logIn(loginCredentials).pipe(
          map(response => AuthenticatedUserDataAccessActions.logInUserSuccess({ loginResponse: response }))
        )
      )
    )
  );

  register$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthenticatedUserDataAccessActions.registerUser),
      switchMap(({ registerCredentials }) =>
        this.authService.register(registerCredentials).pipe(
          map(response => AuthenticatedUserDataAccessActions.registerUserSuccess( { registerResponse: response } ))
        )
      )
    )
  )

  constructor(private readonly actions$: Actions, private authService: AuthService) {}
}
