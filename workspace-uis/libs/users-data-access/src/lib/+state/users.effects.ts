import { Injectable } from '@angular/core';
import { createEffect, Actions, ofType } from '@ngrx/effects';
import * as UsersActions from './users.actions';
import {UsersService} from "@workspace-uis/users-data-access";
import {catchError, map, switchMap} from "rxjs";
import {loadUsersFailure, loadUsersSuccess} from "./users.actions";

@Injectable()
export class UsersEffects {
  init$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UsersActions.initUsers),
      switchMap( () =>
        this.userService.getUsers()
          .pipe(
            map(users => loadUsersSuccess({ users: users })),
            catchError(async (err) => loadUsersFailure({error: err}))
          ),
      )
    )
  );

  constructor(private readonly actions$: Actions, private userService: UsersService) {}
}
