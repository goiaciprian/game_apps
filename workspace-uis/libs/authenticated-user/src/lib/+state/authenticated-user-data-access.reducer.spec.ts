import { Action } from '@ngrx/store';

import * as AuthenticatedUserDataAccessActions from './authenticated-user-data-access.actions';
import { AuthenticatedUserDataAccessEntity } from './authenticated-user-data-access.models';
import {
  AuthenticatedUserDataAccessState,
  initialAuthenticatedUserDataAccessState,
  authenticatedUserDataAccessReducer,
} from './authenticated-user-data-access.reducer';

describe('AuthenticatedUserDataAccess Reducer', () => {
  const createAuthenticatedUserDataAccessEntity = (
    id: string,
    name = ''
  ): AuthenticatedUserDataAccessEntity => ({
    id,
    name: name || `name-${id}`,
  });

  describe('valid AuthenticatedUserDataAccess actions', () => {
    it('loadAuthenticatedUserDataAccessSuccess should return the list of known AuthenticatedUserDataAccess', () => {
      const authenticatedUserDataAccess = [
        createAuthenticatedUserDataAccessEntity('PRODUCT-AAA'),
        createAuthenticatedUserDataAccessEntity('PRODUCT-zzz'),
      ];
      const action =
        AuthenticatedUserDataAccessActions.loadAuthenticatedUserDataAccessSuccess(
          { authenticatedUserDataAccess }
        );

      const result: AuthenticatedUserDataAccessState =
        authenticatedUserDataAccessReducer(
          initialAuthenticatedUserDataAccessState,
          action
        );

      expect(result.loaded).toBe(true);
      expect(result.ids.length).toBe(2);
    });
  });

  describe('unknown action', () => {
    it('should return the previous state', () => {
      const action = {} as Action;

      const result = authenticatedUserDataAccessReducer(
        initialAuthenticatedUserDataAccessState,
        action
      );

      expect(result).toBe(initialAuthenticatedUserDataAccessState);
    });
  });
});
