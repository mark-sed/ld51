package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Player;
import com.sedlacek.ld51.market.Opportunity;

public class OpportunityCard extends GUIObject {
	
	Opportunity opport;
	private BufferedImage bckg, eventBckg;
	public Timer timer;
	private OpportunityBar bar;
	
	public OpportunityCard(Player p, int x, int y, OpportunityBar bar) {
		this.x = x;
		this.y = y;
		this.bckg = ImageLoader.loadNS("/opportunityCard.png");
		this.eventBckg  = ImageLoader.loadNS("/eventCard.png");
		this.w = this.bckg.getWidth()*Config.SIZE_MULT;
		this.h = 60*Config.SIZE_MULT;
		this.bar = bar;
		
		this.opport = Opportunity.randomOpp();
		this.timer = new Timer();
	}
	
	@Override
	public void update() {
		super.update();
		if(timer != null) {
			timer.update(); 
			if(timer.finished) {
				this.newOpport();
			}
		}
	}
	
	public void newOpport() {
		this.timer = new Timer();
		this.opport = Opportunity.randomOpp();
		//new Highlighter(x, y, w, h, false);
	}
	
	@Override
	public void clicked(){
		if(opport.canTake()) {
			this.opport.take();
			this.timer = null;
			this.bar.cardTaken(this);
		}
    }

	@Override
	public void render(Graphics g) {
		y -= (mouseOver && timer != null && opport.canTake()) ? 10*Config.SIZE_MULT: 0;
		if(opport.getID() < 0) {
			g.drawImage(eventBckg, x, y, w, h, null);
		}
		else {
			g.drawImage(bckg, x, y, w, h, null);
		}
		
		if(timer != null)
			timer.render(g, x+4*Config.SIZE_MULT, Config.HEIGHT-3*Config.SIZE_MULT-32, w-8*Config.SIZE_MULT, 3*Config.SIZE_MULT, false);
		
		// Render name
		g.setFont(new Font("DorFont02", Font.BOLD, 6*Config.SIZE_MULT));
		g.setColor(new Color(41, 42, 53));
		g.drawString(opport.getName(), x+w/2-g.getFontMetrics().stringWidth(opport.getName())/2, y+7*Config.SIZE_MULT);
		
		// Render desc
		g.setFont(new Font("DorFont02", Font.PLAIN, 6*Config.SIZE_MULT));
		g.setColor(Color.BLACK);
		int cy = y + 10*Config.SIZE_MULT;
		int space = 6*Config.SIZE_MULT;
		if(opport.getDesc() != null) {
			for(int i = 0; i < opport.getDesc().length; ++i) {
				g.drawString(opport.getDesc()[i], x+4*Config.SIZE_MULT, cy+6*Config.SIZE_MULT);
				cy+=space;
			}
		}
		
		if(!opport.canTake()) {
			g.setColor(new Color(0,0,0,120));
			g.fillRect(x, y, w, h);
		}
		
		y += (mouseOver && timer != null && opport.canTake()) ? 10*Config.SIZE_MULT: 0;
	}

	public void creatEvent() {
		this.timer = new Timer();
		this.opport = Opportunity.randomEvent();
	}

}
