import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameSceneComponent } from './game-scene/game-scene.component';
import { GameComponent } from './game/game.component';
import {GameAssetsModule} from "@workspace-uis/game-assets";

@NgModule({
  imports: [CommonModule, GameAssetsModule],
  declarations: [GameSceneComponent, GameComponent],
  exports: [GameComponent],
})
export class GameContainerModule {}
