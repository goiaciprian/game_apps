import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Action } from '@ngrx/store';
import { provideMockStore } from '@ngrx/store/testing';
import { NxModule } from '@nrwl/angular';
import { hot } from 'jasmine-marbles';
import { Observable } from 'rxjs';

import * as AuthenticatedUserDataAccessActions from './authenticated-user-data-access.actions';
import { AuthenticatedUserDataAccessEffects } from './authenticated-user-data-access.effects';

describe('AuthenticatedUserDataAccessEffects', () => {
  let actions: Observable<Action>;
  let effects: AuthenticatedUserDataAccessEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NxModule.forRoot()],
      providers: [
        AuthenticatedUserDataAccessEffects,
        provideMockActions(() => actions),
        provideMockStore(),
      ],
    });

    effects = TestBed.inject(AuthenticatedUserDataAccessEffects);
  });

  describe('init$', () => {
    it('should work', () => {
      actions = hot('-a-|', {
        a: AuthenticatedUserDataAccessActions.initAuthenticatedUserDataAccess(),
      });

      const expected = hot('-a-|', {
        a: AuthenticatedUserDataAccessActions.loadAuthenticatedUserDataAccessSuccess(
          { authenticatedUserDataAccess: [] }
        ),
      });

      expect(effects.init$).toBeObservable(expected);
    });
  });
});
