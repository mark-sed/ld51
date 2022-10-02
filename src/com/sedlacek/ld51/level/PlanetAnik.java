package com.sedlacek.ld51.level;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Config;

public class PlanetAnik  extends Planet {

	public PlanetAnik(int col, int row) {
		super(col, row);
		this.name = "Planet Anik";
		this.ammoGen = 7;
		this.fuelGen = 3;
		this.desc = new String[] {
			"Vulcanic planet, rich in sulfur.",
			"+"+ammoGen+" ammo",
			"+"+this.fuelGen+" fuel",
		};
		
		this.vision = 1;
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/planets.png"));
		this.icon = this.img = ss.grabImage(4, 1, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
	}

}
