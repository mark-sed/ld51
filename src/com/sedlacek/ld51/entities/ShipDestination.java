package com.sedlacek.ld51.entities;

import java.awt.Graphics;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class ShipDestination extends Entity {

	public ShipDestination(Status s) {
		this.name = "Ship's landing zone";
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/ships.png"));
		this.status = s;
		this.icon = this.img = ss.grabImage(2, 2, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}

}
