import { NgModule } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule, Store } from '@ngrx/store';
import { NxModule } from '@nrwl/angular';
import { readFirst } from '@nrwl/angular/testing';

import * as AuthenticatedUserDataAccessActions from './authenticated-user-data-access.actions';
import { AuthenticatedUserDataAccessEffects } from './authenticated-user-data-access.effects';
import { AuthenticatedUserDataAccessFacade } from './authenticated-user-data-access.facade';
import { AuthenticatedUserDataAccessEntity } from './authenticated-user-data-access.models';
import {
  AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY,
  AuthenticatedUserDataAccessState,
  initialAuthenticatedUserDataAccessState,
  authenticatedUserDataAccessReducer,
} from './authenticated-user-data-access.reducer';
import * as AuthenticatedUserDataAccessSelectors from './authenticated-user-data-access.selectors';

interface TestSchema {
  authenticatedUserDataAccess: AuthenticatedUserDataAccessState;
}

describe('AuthenticatedUserDataAccessFacade', () => {
  let facade: AuthenticatedUserDataAccessFacade;
  let store: Store<TestSchema>;
  const createAuthenticatedUserDataAccessEntity = (
    id: string,
    name = ''
  ): AuthenticatedUserDataAccessEntity => ({
    id,
    name: name || `name-${id}`,
  });

  describe('used in NgModule', () => {
    beforeEach(() => {
      @NgModule({
        imports: [
          StoreModule.forFeature(
            AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY,
            authenticatedUserDataAccessReducer
          ),
          EffectsModule.forFeature([AuthenticatedUserDataAccessEffects]),
        ],
        providers: [AuthenticatedUserDataAccessFacade],
      })
      class CustomFeatureModule {}

      @NgModule({
        imports: [
          NxModule.forRoot(),
          StoreModule.forRoot({}),
          EffectsModule.forRoot([]),
          CustomFeatureModule,
        ],
      })
      class RootModule {}
      TestBed.configureTestingModule({ imports: [RootModule] });

      store = TestBed.inject(Store);
      facade = TestBed.inject(AuthenticatedUserDataAccessFacade);
    });

    /**
     * The initially generated facade::loadAll() returns empty array
     */
    it('loadAll() should return empty list with loaded == true', async () => {
      let list = await readFirst(facade.allAuthenticatedUserDataAccess$);
      let isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(false);

      facade.init();

      list = await readFirst(facade.allAuthenticatedUserDataAccess$);
      isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(true);
    });

    /**
     * Use `loadAuthenticatedUserDataAccessSuccess` to manually update list
     */
    it('allAuthenticatedUserDataAccess$ should return the loaded list; and loaded flag == true', async () => {
      let list = await readFirst(facade.allAuthenticatedUserDataAccess$);
      let isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(0);
      expect(isLoaded).toBe(false);

      store.dispatch(
        AuthenticatedUserDataAccessActions.loadAuthenticatedUserDataAccessSuccess(
          {
            authenticatedUserDataAccess: [
              createAuthenticatedUserDataAccessEntity('AAA'),
              createAuthenticatedUserDataAccessEntity('BBB'),
            ],
          }
        )
      );

      list = await readFirst(facade.allAuthenticatedUserDataAccess$);
      isLoaded = await readFirst(facade.loaded$);

      expect(list.length).toBe(2);
      expect(isLoaded).toBe(true);
    });
  });
});
