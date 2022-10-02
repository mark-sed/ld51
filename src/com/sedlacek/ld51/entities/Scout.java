package com.sedlacek.ld51.entities;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class Scout extends Ship {

	public Scout(int col, int row, Status stat) {
		super(col, row, stat);
		this.type = ShipType.SCOUT;
		this.name = "Scouting ship";
		this.desc = new String[] {
			"Fast ship with great scanners.",
		};
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/ships.png"));
		
		this.icon = this.img = ss.grabImage(1, 4, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		
		this.vision = 5;
		this.speed = 8;
		
		this.maxHp = 50;
		this.hp = this.maxHp;
	}

}
