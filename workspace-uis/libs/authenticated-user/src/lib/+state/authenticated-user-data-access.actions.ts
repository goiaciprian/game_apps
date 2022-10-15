import { createAction, props } from '@ngrx/store';
import { RegisterLoginEntity } from './authenticated-user-data-access.models';
import {LoginRegisterResponseModel} from "./loginregisterresponse.models";


export const logInUser = createAction(
  '[AuthenticatedUserDataAccess/API] Try Login User',
  props<{ loginCredentials: RegisterLoginEntity }>()
);


export const logInUserSuccess = createAction(
  '[AuthenticatedUserDataAccess/API] Login User Success',
  props<{ loginResponse: LoginRegisterResponseModel }>()
);

export const registerUser = createAction(
  '[AuthenticatedUserDataAccess/API] Try register User',
  props<{ registerCredentials: RegisterLoginEntity }>()
);


export const registerUserSuccess = createAction(
  '[AuthenticatedUserDataAccess/API] Register User Success',
  props<{ registerResponse: LoginRegisterResponseModel }>()
);

