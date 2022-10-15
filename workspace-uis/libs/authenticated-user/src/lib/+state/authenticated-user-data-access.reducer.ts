import { createReducer, on, Action } from '@ngrx/store';
import * as AuthActions from "./authenticated-user-data-access.actions";

export const AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY =
  'authenticatedUserDataAccess';

export interface AuthenticatedUserDataAccessState {
  access_token: string | null;
  username: string | null
}

export interface AuthenticatedUserDataAccessPartialState {
  readonly [AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY]: AuthenticatedUserDataAccessState;
}

export const initialAuthenticatedUserDataAccessState: AuthenticatedUserDataAccessState = {
  access_token: null,
  username: null
}

const reducer = createReducer(
  initialAuthenticatedUserDataAccessState,
  on(AuthActions.logInUserSuccess, (state, { loginResponse }) => ({
    username: loginResponse.username,
    access_token: loginResponse.token
  })),
  on(AuthActions.registerUserSuccess, (state, { registerResponse }) => ({
    username: registerResponse.username,
    access_token: registerResponse.token
  }))
);

export function authenticatedUserDataAccessReducer(
  state: AuthenticatedUserDataAccessState | undefined,
  action: Action
) {
  return reducer(state, action);
}
