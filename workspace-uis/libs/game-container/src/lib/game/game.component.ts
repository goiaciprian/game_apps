import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import * as Phaser from 'phaser';
import {GameSceneComponent} from "../game-scene/game-scene.component";

@Component({
  selector: 'workspace-uis-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss'],
  encapsulation: ViewEncapsulation.Emulated,
})
export class GameComponent implements OnInit {
  phaserGame: Phaser.Game | undefined;
  config: Phaser.Types.Core.GameConfig;

  constructor() {
    this.config = {
      type: Phaser.WEBGL,
      height: 850,
      width: 1580,
      scene: [ GameSceneComponent ],
      parent: 'gameContainer',
      backgroundColor: '#63fffa',
    }
  }

  ngOnInit(): void {
    this.phaserGame = new Phaser.Game(this.config)
  }
}
