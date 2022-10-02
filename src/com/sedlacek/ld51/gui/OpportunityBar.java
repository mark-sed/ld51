package com.sedlacek.ld51.gui;

import java.awt.Graphics;
import java.util.ArrayList;

import com.sedlacek.ld51.main.AudioGalery;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.Player;

public class OpportunityBar extends GUIObject {

	public ArrayList<OpportunityCard> opports;
	private Timer timer;
	
	int startx = (4+96+10)*Config.SIZE_MULT;
	int starty = Config.HEIGHT-60*Config.SIZE_MULT+32;
	
	private OpportunityCard taken;
	private boolean timerFinished;
	
	private Player p;
	
	public OpportunityBar(Player p) {
		this.p = p;
		this.opports = new ArrayList<OpportunityCard>();
		
		this.createCard();
		
		timer = new Timer();
		this.timerFinished = false;
	}
	
	public void createCard() {
		this.opports.add(new OpportunityCard(p, startx+48*Config.SIZE_MULT*(this.opports.size())+(2*this.opports.size())*Config.SIZE_MULT, starty, this));
	}
	
	@Override
	public void update() {
		if(timer.finished) {
			if(!timerFinished) {
				for(OpportunityCard c: opports) {
					c.newOpport();
				}
				timerFinished = true;
			}
			for(OpportunityCard c: opports) {
				c.update();
			}
		}
		else {
			timer.update();
		}
	}
	
	public void cardTaken(OpportunityCard c) {
		timer = new Timer();
		this.taken = c;
		this.timerFinished = false;
		Game.log.add("Opportunity "+c.opport.getName()+" taken.");
		if(Game.soundOn)
    		AudioGalery.card.playClip();
	}
	
	public void triggerEvent() {
		for(OpportunityCard oc: opports) {
			oc.creatEvent();
		}
		this.timerFinished = true;
		this.timer.setS(1);
		Game.log.add("New event has appeared!");
	}
	
	@Override
	public void render(Graphics g) {
		if(timer.finished) {
			for(OpportunityCard c: opports) {
				c.render(g);
			}
		}
		else {
			if(taken != null && timer.getS() > 9000) {
				taken.render(g);
			}
			timer.render(g, startx, Config.HEIGHT-5*Config.SIZE_MULT-32-2*Config.SIZE_MULT, 200*Config.SIZE_MULT, 5*Config.SIZE_MULT, false);
		}
	}
}
