import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {GameContainerModule} from "@workspace-uis/game-container";

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, GameContainerModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
