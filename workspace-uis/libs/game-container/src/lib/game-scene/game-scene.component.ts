import { Component, ViewEncapsulation } from '@angular/core';
import * as Phaser from 'phaser';
import { TypesTile } from "@workspace-uis/game-assets";
import map from '../configs/map.json';

@Component({
  selector: 'workspace-uis-game-scene',
  encapsulation: ViewEncapsulation.Emulated,
  template: ``
})
export class GameSceneComponent extends Phaser.Scene {

  constructor() {
    super({ key: 'main'});
  }

  create() {
    this.buildMap();
    this.cameraMovementsAndZoom();
    this.input.on('gameobjectup', (pointer: Phaser.Input.Pointer, gameObj: Phaser.GameObjects.Image) => {
      if(pointer.leftButtonReleased())
        gameObj.emit('click', gameObj);
    }, this);
  }

  preload() {
    this.load.image(TypesTile.ground_grass, TypesTile.ground_grass);
    this.load.image(TypesTile.ground_grass_puddle, TypesTile.ground_grass_puddle);
    this.load.json('map', map);
  }

  override update() {
  }

  setTexture(tile: Phaser.GameObjects.Image) {
    if(tile.texture.has(TypesTile.ground_grass_puddle))
      tile.setTexture(TypesTile.ground_grass);
    else
      tile.setTexture(TypesTile.ground_grass_puddle);
  }

  buildMap() {
    const data = this.cache.json.get('map') ;

    const tilewidth = data.tilewidth;
    const tileheight = data.tileheight;

    const tileWidthHalf = tilewidth / 2;
    const tileHeightHalf = tileheight / 2;

    const mapwidth = data.layers[0].width;
    const mapheight = data.layers[0].height;

    const centerX = mapwidth * tileWidthHalf;
    const centerY = 0;

    for (let y = 0; y < mapheight; y++)
    {
      for (let x = 0; x < mapwidth; x++)
      {

        const tx = (x-y) * tileWidthHalf;
        const ty = (x+y) * tileHeightHalf;

        const tile = this.add.image(centerX + tx, centerY + ty, TypesTile.ground_grass);
        tile.setInteractive();
        tile.on('click', this.setTexture, this);
        tile.depth = centerY + ty;
      }
    }
  }

  cameraMovementsAndZoom() {
    this.input.on("wheel",  (
      pointer: Phaser.Input.Pointer,
      gameObjects: Phaser.GameObjects.GameObject,
      deltaX: number,
      deltaY: number,
      deltaZ: number) => {

      if (deltaY > 0) {
        const newZoom = this.cameras.main.zoom - .1
        if (newZoom > 0.3) {
          this.cameras.main.zoom = newZoom
        }
      }

      if (deltaY < 0) {
        const newZoom = this.cameras.main.zoom +.1;
        if (newZoom < 1.3) {
          this.cameras.main.zoom = newZoom;
        }
      }

    });

    this.input.on('pointermove', (pointer: Phaser.Input.Pointer) => {
      if (!pointer.isDown) return;

      if(pointer.middleButtonDown()) {
        this.cameras.main.scrollX -= (pointer.x - pointer.prevPosition.x) / this.cameras.main.zoom;
        this.cameras.main.scrollY -= (pointer.y - pointer.prevPosition.y) / this.cameras.main.zoom;
      }
    });

  }

}

