package com.sedlacek.ld51.entities;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class EnemyBase extends Ship {
	
	
	public EnemyBase(int col, int row, Status stat) {
		super(col, row, stat);
		this.type = ShipType.BASE;
		this.name = "Enemy Mother ship";
		this.desc = new String[] {
			"Enemy base of operations."	
		};
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/ships.png"));
		
		this.icon = this.img = ss.grabImage(2, 3, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		
		this.vision = 4;
		this.speed = 3;
		
		this.maxHp = 1000;
		this.hp = this.maxHp;
		
		this.attackSpeed = 1000;
		this.dmg = 10;
	}

}
