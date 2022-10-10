import { createFeatureSelector, createSelector } from '@ngrx/store';
import { USERS_FEATURE_KEY, UsersState } from './users.reducer';

// Lookup the 'Users' feature state managed by NgRx
export const getUsersState =
  createFeatureSelector<UsersState>(USERS_FEATURE_KEY);

export const getUsersLoaded = createSelector(
  getUsersState,
  (state: UsersState) => state.loaded
);

export const getUsersError = createSelector(
  getUsersState,
  (state: UsersState) => state.error
);

export const getAllUsers = createSelector(
  getUsersState,
  (state: UsersState) => state.users
);

