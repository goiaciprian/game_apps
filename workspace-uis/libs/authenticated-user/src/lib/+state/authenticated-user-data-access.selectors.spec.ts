import { AuthenticatedUserDataAccessEntity } from './authenticated-user-data-access.models';
import {
  authenticatedUserDataAccessAdapter,
  AuthenticatedUserDataAccessPartialState,
  initialAuthenticatedUserDataAccessState,
} from './authenticated-user-data-access.reducer';
import * as AuthenticatedUserDataAccessSelectors from './authenticated-user-data-access.selectors';

describe('AuthenticatedUserDataAccess Selectors', () => {
  const ERROR_MSG = 'No Error Available';
  const getAuthenticatedUserDataAccessId = (
    it: AuthenticatedUserDataAccessEntity
  ) => it.id;
  const createAuthenticatedUserDataAccessEntity = (id: string, name = '') =>
    ({
      id,
      name: name || `name-${id}`,
    } as AuthenticatedUserDataAccessEntity);

  let state: AuthenticatedUserDataAccessPartialState;

  beforeEach(() => {
    state = {
      authenticatedUserDataAccess: authenticatedUserDataAccessAdapter.setAll(
        [
          createAuthenticatedUserDataAccessEntity('PRODUCT-AAA'),
          createAuthenticatedUserDataAccessEntity('PRODUCT-BBB'),
          createAuthenticatedUserDataAccessEntity('PRODUCT-CCC'),
        ],
        {
          ...initialAuthenticatedUserDataAccessState,
          selectedId: 'PRODUCT-BBB',
          error: ERROR_MSG,
          loaded: true,
        }
      ),
    };
  });

  describe('AuthenticatedUserDataAccess Selectors', () => {
    it('getAllAuthenticatedUserDataAccess() should return the list of AuthenticatedUserDataAccess', () => {
      const results =
        AuthenticatedUserDataAccessSelectors.getAllAuthenticatedUserDataAccess(
          state
        );
      const selId = getAuthenticatedUserDataAccessId(results[1]);

      expect(results.length).toBe(3);
      expect(selId).toBe('PRODUCT-BBB');
    });

    it('getSelected() should return the selected Entity', () => {
      const result = AuthenticatedUserDataAccessSelectors.getSelected(
        state
      ) as AuthenticatedUserDataAccessEntity;
      const selId = getAuthenticatedUserDataAccessId(result);

      expect(selId).toBe('PRODUCT-BBB');
    });

    it('getAuthenticatedUserDataAccessLoaded() should return the current "loaded" status', () => {
      const result =
        AuthenticatedUserDataAccessSelectors.getAuthenticatedUserDataAccessLoaded(
          state
        );

      expect(result).toBe(true);
    });

    it('getAuthenticatedUserDataAccessError() should return the current "error" state', () => {
      const result =
        AuthenticatedUserDataAccessSelectors.getAuthenticatedUserDataAccessError(
          state
        );

      expect(result).toBe(ERROR_MSG);
    });
  });
});
