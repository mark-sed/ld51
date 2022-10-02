package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Player;

public class Market extends GUIObject {

	private BufferedImage header;
	private MarketOffer[] offers;
	
	public Market(Player p) {
		this.header = ImageLoader.loadNS("/marketHeader.png");
		
		offers = new MarketOffer[3];
		
		int startx = Config.WIDTH-(87-16-5)*Config.SIZE_MULT;
		int starty = 12*Config.SIZE_MULT+Config.TILE_SIZE*Config.SIZE_MULT;
		offers[0] = new MarketOffer(p, startx, starty+2*Config.SIZE_MULT);
		offers[1] = new MarketOffer(p, startx, starty+4*Config.SIZE_MULT+36*Config.SIZE_MULT);
		offers[2] = new MarketOffer(p, startx, starty+6*Config.SIZE_MULT+36*Config.SIZE_MULT*2);
	}
	
	@Override
	public void update() {
		for(MarketOffer o: offers) {
			o.update();
		}
	}

	@Override
	public void render(Graphics g) {
		int startx = Config.WIDTH-(87-16-5)*Config.SIZE_MULT;
		int starty = (12+8-5)*Config.SIZE_MULT;
		g.drawImage(this.header, startx, starty, this.header.getWidth()*Config.SIZE_MULT, this.header.getHeight()*Config.SIZE_MULT, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("DorFont03", Font.BOLD, 7*Config.SIZE_MULT));
		g.drawString("Market offers:", startx+4*Config.SIZE_MULT, starty+8*Config.SIZE_MULT);
		for(MarketOffer o: offers) {
			o.render(g);
		}
	}

}
