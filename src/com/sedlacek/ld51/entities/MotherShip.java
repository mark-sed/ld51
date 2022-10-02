package com.sedlacek.ld51.entities;

import java.awt.image.BufferedImage;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public class MotherShip extends Ship {
	
	private BufferedImage lvl1Im, lvl2Im, lvl3Im;
	
	private long lastTime;
	
	public MotherShip(int col, int row, Status stat) {
		super(col, row, stat);
		this.type = ShipType.BASE;
		this.name = "Mother ship";
		this.desc = new String[] {
			"Base of operations."	
		};
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/ships.png"));
		
		this.icon = this.img = ss.grabImage(1, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		this.lvl1Im = ss.grabImage(1, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		this.lvl2Im = ss.grabImage(2, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		this.lvl3Im = ss.grabImage(3, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		
		this.vision = 3;
		this.speed = 2;
		
		this.maxHp = 300;
		this.hp = this.maxHp;
		this.attackSpeed = 900;
		this.dmg = 1;
	}
	
	@Override
	public void update() {
		super.update();
		if(Game.player.level <= 1) {
			this.icon = this.img = lvl1Im;
		}
		else if(Game.player.level == 2) {
			this.icon = this.img = lvl2Im;
		}
		else {
			this.icon = this.img = lvl3Im;
		}
	}

	@Override
	public void setHp(int hp) {
		if(hp < this.hp && System.currentTimeMillis() - lastTime >= 3000) {
			Game.log.add("Mother ship is under attack!");
			lastTime = System.currentTimeMillis();
		}
		this.hp = hp;
	}
}
