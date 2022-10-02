package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.main.AudioGalery;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.Player;
import com.sedlacek.ld51.market.Offer;

public class MarketOffer extends GUIObject {

	private BufferedImage bckg;
	private Offer offer;
	private Timer timer;
	
	public MarketOffer(Player p, int x, int y) {
		this.x = x;
		this.y = y;
		this.bckg = ImageLoader.loadNS("/marketOffer.png");
		this.w = this.bckg.getWidth()*Config.SIZE_MULT;
		this.h = this.bckg.getHeight()*Config.SIZE_MULT;
		
		this.offer = Offer.randomOffer();
		this.timer = new Timer();
	}
	
	@Override
	public void update() {
		super.update();
		timer.update();
		if(timer.finished) {
			this.newOffer();
		}
	}
	
	public void newOffer() {
		this.timer = new Timer();
		this.offer = Offer.randomOffer();
		new Highlighter(x, y, w, h, false);
	}
	
	@Override
	public void clicked(){
		if(offer.canBuy()) {
			Game.log.add("Transaction with "+offer.getSeller().getName()+" was successful.");
			this.offer.buy();
			this.offer.getSeller().bought();
			this.newOffer();
			if(Game.soundOn)
	    		AudioGalery.trade.playClip();
		}
		else {
			offer.highlightMissing();
		}
    }

	@Override
	public void render(Graphics g) {
		g.drawImage(bckg, x, y, w, h, null);
		if(mouseOver && offer.canBuy()) {
			g.setColor(new Color(255,255,255,100));
			g.fillRect(x, y, w, h);
		}
		
		// Render offer
		g.setFont(new Font("DorFont02", Font.PLAIN, 8*Config.SIZE_MULT));
		g.setColor(new Color(61, 42, 14));
		g.drawString(offer.getSeller().getName(), x+w/2-g.getFontMetrics().stringWidth(offer.getSeller().getName())/2, y+7*Config.SIZE_MULT);
	
		// Render offer pros and cons
		int lines = Math.max(offer.getPlus().length+offer.getSeller().getPlus().length, 
				offer.getMinus().length+offer.getSeller().getMinus().length);
		
		int cx = x+5*Config.SIZE_MULT;
		int cy = y+9*Config.SIZE_MULT;
		if(lines < 3) {
			cy += 7*Config.SIZE_MULT;
		}
		int space = 9*Config.SIZE_MULT;
		g.setFont(new Font("DorFont02", Font.BOLD, 6*Config.SIZE_MULT));
		g.setColor(Color.BLACK);
		for(int i = 0; i < offer.getPlus().length; ++i) {
			if(i < offer.getPlusIc().length && offer.getPlusIc()[i] != null) {
				g.drawImage(offer.getPlusIc()[i], cx, cy, 8*Config.SIZE_MULT, 8*Config.SIZE_MULT, null);
			}
			g.drawString("+"+offer.getPlus()[i], cx+9*Config.SIZE_MULT, cy+6*Config.SIZE_MULT);
			cy+=space;
		}
		for(int i = 0; i < offer.getSeller().getPlus().length; ++i) {
			if(i < offer.getSeller().getPlusIc().length && offer.getPlusIc()[i] != null) {
				g.drawImage(offer.getSeller().getPlusIc()[i], cx, cy, 8*Config.SIZE_MULT, 8*Config.SIZE_MULT, null);
			}
			g.drawString("+"+offer.getSeller().getPlus()[i], cx+9*Config.SIZE_MULT, cy+6*Config.SIZE_MULT);
			cy+=space;
		}
		
		cy = y+9*Config.SIZE_MULT;
		if(lines < 3) {
			cy += 7*Config.SIZE_MULT;
		}
		cx = x+w/2;
		for(int i = 0; i < offer.getMinus().length; ++i) {
			if(i < offer.getMinusIc().length && offer.getPlusIc()[i] != null) {
				g.drawImage(offer.getMinusIc()[i], cx, cy, 8*Config.SIZE_MULT, 8*Config.SIZE_MULT, null);
			}
			g.drawString("-"+offer.getMinus()[i], cx+9*Config.SIZE_MULT, cy+6*Config.SIZE_MULT);
			cy+=space;
		}
		for(int i = 0; i < offer.getSeller().getMinus().length; ++i) {
			if(i < offer.getSeller().getMinusIc().length && offer.getPlusIc()[i] != null) {
				g.drawImage(offer.getSeller().getMinusIc()[i], cx, cy, 8*Config.SIZE_MULT, 8*Config.SIZE_MULT, null);
			}
			g.drawString("-"+offer.getSeller().getMinus()[i], cx+9*Config.SIZE_MULT, cy+6*Config.SIZE_MULT);
			cy+=space;
		}
		
		if(!offer.canBuy()) {
			g.setColor(new Color(0,0,0,120));
			g.fillRect(x, y, w, h);
		}

		timer.render(g, x, y+h-3*Config.SIZE_MULT, w, 3*Config.SIZE_MULT, true);
	}

}
