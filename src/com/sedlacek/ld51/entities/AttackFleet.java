package com.sedlacek.ld51.entities;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class AttackFleet extends Ship {

	public AttackFleet(int col, int row, Status stat) {
		super(col, row, stat);
		this.type = ShipType.ATTACK;
		this.name = "Attack fleet";
		this.desc = new String[] {
			"Group of fighter jets.",
		};
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/ships.png"));
		
		if(stat == Status.FRIENDLY)
			this.icon = this.img = ss.grabImage(1, 5, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		else
			this.icon = this.img = ss.grabImage(2, 5, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		this.vision = 2;
		this.speed = 4;
		
		this.maxHp = 25;
		this.hp = this.maxHp;
		
		this.attackSpeed = 500;
		this.dmg = 2;
	}

}
