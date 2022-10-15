import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import * as fromAuthenticatedUserDataAccess from './+state/authenticated-user-data-access.reducer';
import { AuthenticatedUserDataAccessEffects } from './+state/authenticated-user-data-access.effects';
import { AuthenticatedUserDataAccessFacade } from './+state/authenticated-user-data-access.facade';
import { HttpClientModule} from "@angular/common/http";

@NgModule({
  imports: [
    CommonModule,
    StoreModule.forFeature(
      fromAuthenticatedUserDataAccess.AUTHENTICATED_USER_DATA_ACCESS_FEATURE_KEY,
      fromAuthenticatedUserDataAccess.authenticatedUserDataAccessReducer
    ),
    EffectsModule.forFeature([AuthenticatedUserDataAccessEffects]),
    HttpClientModule
  ],
  providers: [AuthenticatedUserDataAccessFacade],
})
export class AuthenticatedUserModule {}
