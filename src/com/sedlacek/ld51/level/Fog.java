package com.sedlacek.ld51.level;

import java.awt.Graphics;

import com.sedlacek.ld51.main.GameObject;
import com.sedlacek.ld51.market.Offer;

public class Fog extends GameObject {

	public Fog() {
		this.name = "Nebula";
		this.desc = new String[] {
			"This space is covered by a nebula.",
			"Move units closer to update",
			"this location."
		};
		this.icon = Offer.iconNebula;
		this.status = Status.UNKNOWN;
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
