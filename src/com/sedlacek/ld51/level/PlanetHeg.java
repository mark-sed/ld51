package com.sedlacek.ld51.level;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class PlanetHeg extends Planet {

	public PlanetHeg(int col, int row) {
		super(col, row);
		this.name = "Planet Heg";
		this.moneyGen = 2;
		this.desc = new String[] {
			"Frozen planet with huge ice caps.",
			"Generates:",
			"+"+this.moneyGen+" money",
		};
		
		this.vision = 1;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/planets.png"));
		this.icon = this.img = ss.grabImage(2, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}

}
