import { createFeatureSelector, createSelector } from '@ngrx/store';
import {
  AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY,
  AuthenticatedUserDataAccessState,
} from './authenticated-user-data-access.reducer';
import {Observable} from "rxjs";

// Lookup the 'AuthenticatedUserDataAccess' feature state managed by NgRx
export const getAuthenticatedUserDataAccessState =
  createFeatureSelector<AuthenticatedUserDataAccessState>(
    AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY
  );

export const getAccessToken = createSelector(
  getAuthenticatedUserDataAccessState,
  (state) => state.access_token
)

export const isAuthenticated = createSelector(
  getAuthenticatedUserDataAccessState,
  (state) => state.access_token !== null
)

export const getUsername = createSelector(
  getAuthenticatedUserDataAccessState,
  (state) => state.username
)
