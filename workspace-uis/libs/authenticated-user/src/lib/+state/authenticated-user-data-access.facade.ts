import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import {getAccessToken, getUsername, isAuthenticated} from "./authenticated-user-data-access.selectors";
import {RegisterLoginEntity} from "./authenticated-user-data-access.models";
import {logInUser, registerUser} from "./authenticated-user-data-access.actions";


@Injectable()
export class AuthenticatedUserDataAccessFacade {
  /**
   * Combine pieces of state using createSelector,
   * and expose them as observables through the facade.
   */
  getAccessToken$ = this.store.pipe(select(getAccessToken));
  isAuthenticated$ = this.store.pipe(select(isAuthenticated));
  getUsername = this.store.pipe(select(getUsername));

  constructor(private readonly store: Store) {}

  /**
   * Use the initialization action to perform one
   * or more tasks in your Effects.
   */
  registerUser(credentials: RegisterLoginEntity) {
    this.store.dispatch(registerUser({ registerCredentials: credentials }))
  }

  loginUser(credentials: RegisterLoginEntity) {
    this.store.dispatch(logInUser({ loginCredentials: credentials }))
  }
}
