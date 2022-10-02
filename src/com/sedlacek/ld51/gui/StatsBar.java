package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.Player;

public class StatsBar extends GUIObject {

	private BufferedImage bar;
	private Player p;
	
	public Timer moneyTimer, foodTimer, pplTimer; 
	private Random r = new Random();
	
	public StatsBar(Player p) {
		this.p = p;
		this.bar = ImageLoader.loadNS("/statbar.png");
		this.moneyTimer = new Timer();
		this.foodTimer = new Timer();
		this.pplTimer = new Timer();
	}
	
	public void highlightMoney(boolean good) {
		new Highlighter(9*Config.SIZE_MULT, 1*Config.SIZE_MULT, 16*Config.SIZE_MULT, 6*Config.SIZE_MULT, good);
	}
	
	public void highlightFood(boolean good) {
		new Highlighter(36*Config.SIZE_MULT, 1*Config.SIZE_MULT, 16*Config.SIZE_MULT, 6*Config.SIZE_MULT, good);
	}
	
	public void highlightFuel(boolean good) {
		new Highlighter(64*Config.SIZE_MULT, 1*Config.SIZE_MULT, 16*Config.SIZE_MULT, 6*Config.SIZE_MULT, good);
	}
	
	public void highlightAmmo(boolean good) {
		new Highlighter(92*Config.SIZE_MULT, 1*Config.SIZE_MULT, 16*Config.SIZE_MULT, 6*Config.SIZE_MULT, good);
	}
	
	public void highlightPpl(boolean good) {
		new Highlighter(120*Config.SIZE_MULT, 1*Config.SIZE_MULT, 16*Config.SIZE_MULT, 6*Config.SIZE_MULT, good);
	}
	
	public void highlightTI(boolean good) {
		new Highlighter(301*Config.SIZE_MULT, 1*Config.SIZE_MULT, 16*Config.SIZE_MULT, 6*Config.SIZE_MULT, good);
	}
	
	public void makeMoney() {
		// TODO: Later on count in colonies
		int am = Game.player.passiveIncome + Game.player.extraIncome + Game.player.getPpl()/5;
		Game.player.giveMoney(am);
	}
	
	public void makePpl() {
		if(p.level < 2 && r.nextBoolean()) {
			Game.player.givePpl(1);
		}
		else {
			Game.player.givePpl(1);
		}
	}
	
	public void eatFood() {
		int am = (int)((p.getPpl()/2)*p.foodMult)+Game.player.fleet.size()*2;
		am = am == 0 ? 1 : am;
		// Am might become negative - generating food
		am -= p.farming;
		
		if(p.getFood() < am) {
			p.setFood(0);
			int dies = (int)(p.getPpl()/3);
			dies = dies > 1 ? dies : 2;
			if(p.getPpl() - dies < 0) {
				p.setPpl(0);
			}
			else {
				p.takePpl(dies);
			}
			highlightFood(false);
			Game.log.add("Your population is starving.");
			Game.log.add("Obtain more food!");
		}
		else {
			p.takeFood(am);
		}
		
	}
	
	@Override
	public void update() {
		moneyTimer.update();
		if(moneyTimer.finished) {
			makeMoney();
			this.moneyTimer = new Timer();
		}
		foodTimer.update();
		if(foodTimer.finished) {
			eatFood();
			this.foodTimer = new Timer();
		}
		pplTimer.update();
		if(pplTimer.finished) {
			makePpl();
			this.pplTimer = new Timer();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(this.bar, 0, 0, Config.WIDTH, this.bar.getHeight()*Config.SIZE_MULT, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("DorFont02", Font.BOLD, 7*Config.SIZE_MULT));
		g.drawString(p.getMoney()+"", 10*Config.SIZE_MULT, 6*Config.SIZE_MULT);
		g.drawString(p.getFood()+"", 37*Config.SIZE_MULT, 6*Config.SIZE_MULT);
		g.drawString(p.getFuel()+"", 65*Config.SIZE_MULT, 6*Config.SIZE_MULT);
		g.drawString(p.getAmmo()+"", 93*Config.SIZE_MULT, 6*Config.SIZE_MULT);
		g.drawString(p.getPpl()+"", 121*Config.SIZE_MULT, 6*Config.SIZE_MULT);
		
		g.drawString((int)(p.getTradingIndex()*100)+"", 302*Config.SIZE_MULT, 6*Config.SIZE_MULT);
		
		moneyTimer.render(g, 9*Config.SIZE_MULT, 7*Config.SIZE_MULT, 16*Config.SIZE_MULT, 1*Config.SIZE_MULT, false);
		foodTimer.render(g, 36*Config.SIZE_MULT, 7*Config.SIZE_MULT, 16*Config.SIZE_MULT, 1*Config.SIZE_MULT, false);
		pplTimer.render(g, 120*Config.SIZE_MULT, 7*Config.SIZE_MULT, 16*Config.SIZE_MULT, 1*Config.SIZE_MULT, false);
	}

}
