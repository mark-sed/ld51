package com.sedlacek.ld51.level;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class PlanetEdems extends Planet {

	public PlanetEdems(int col, int row) {
		super(col, row);
		this.name = "Planet Edems";
		this.TIGen = 0.01f;
		this.fuelGen = 2;
		this.ammoGen = 1;
		this.desc = new String[] {
			"Planet without any sunlight, but",
			"it is the center of all trade.",
			"+"+(int)(this.TIGen*100)+" trading index",
			"+"+this.fuelGen+" fuel",
		};
		
		this.vision = 1;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/planets.png"));
		this.icon = this.img = ss.grabImage(3, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}

}
