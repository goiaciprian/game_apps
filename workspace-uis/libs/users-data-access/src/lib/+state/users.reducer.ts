import { createReducer, on, Action } from '@ngrx/store';

import * as UsersActions from './users.actions';
import { UsersEntity } from './users.models';

export const USERS_FEATURE_KEY = 'users';

export interface UsersState {
  users: UsersEntity[]
  loaded: boolean; // has the Users list been loaded
  error?: any | null; // last known error (if any)
}

export interface UsersPartialState {
  readonly [USERS_FEATURE_KEY]: UsersState;
}

export const initialUsersState: UsersState = {
  users: [],
  loaded: false,
  error: null
};

const reducer = createReducer(
  initialUsersState,
  on(UsersActions.initUsers, (state) => ({
    ...state,
    loaded: false,
    error: null,
  })),
  on(UsersActions.loadUsersSuccess, (state, { users }) =>
    ({...state, loaded: true, users: users})
  ),
  on(UsersActions.loadUsersFailure, (state, { error }) => ({ ...state, error }))
);

export function usersReducer(state: UsersState | undefined, action: Action) {
  return reducer(state, action);
}
