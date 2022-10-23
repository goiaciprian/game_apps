import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import { StoreRouterConnectingModule } from '@ngrx/router-store';
import {
  UsersDataAccessModule,
  usersReducer,
} from '@workspace-uis/users-data-access';
import { ENVIRONMENT_CONFIG } from '@workspace-uis/configurations';
import { LoginRegisterPageComponent } from './Pages/login-register-page/login-register-page.component';
import { routes } from './app.routing';
import { AuthenticateUiModule } from '@workspace-uis/authenticate-ui';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CodeProblemsUiModule } from '@workspace-uis/code-problems-ui';
import { CodeProblemsPageComponent } from './Pages/code-problems-page/code-problems-page.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginRegisterPageComponent,
    CodeProblemsPageComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, { initialNavigation: 'enabledBlocking' }),
    StoreModule.forRoot(
      {
        users: usersReducer,
      },
      {
        metaReducers: !environment.production ? [] : [],
        runtimeChecks: {
          strictActionImmutability: true,
          strictStateImmutability: true,
        },
      }
    ),
    EffectsModule.forRoot([]),
    !environment.production ? StoreDevtoolsModule.instrument() : [],
    StoreRouterConnectingModule.forRoot(),
    UsersDataAccessModule,
    AuthenticateUiModule,
    BrowserAnimationsModule,
    CodeProblemsUiModule,
  ],
  providers: [{ provide: ENVIRONMENT_CONFIG, useValue: environment }],
  bootstrap: [AppComponent],
})
export class AppModule {}
