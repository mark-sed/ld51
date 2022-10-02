package com.sedlacek.ld51.level;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class PlanetDream extends Planet {

	public PlanetDream(int col, int row) {
		super(col, row);
		this.name = "Planet Dream";
		this.foodGen = 4;
		this.pplGen = 1;
		this.desc = new String[] {
			"Humid planet with vast farmlands.",
			"Generates:",
			"+"+this.foodGen+" food",
			"+"+this.pplGen+" people"
		};
		
		this.vision = 1;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/planets.png"));
		this.icon = this.img = ss.grabImage(1, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}
	
}
