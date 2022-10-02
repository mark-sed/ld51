package com.sedlacek.ld51.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.main.AudioGalery;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public class Laser extends GUIObject {

	private int transp;
	private int decay;
	private boolean lightUp;
	private int r, g, b;
	private int x2, y2;
	private long lastTime;
	
	private static Random rnd = new Random();
	
	public Laser(int x, int y, int x2, int y2, boolean good) {
		init(x, y, x2, y2);
		if(good) {
			this.r = 99;
			this.g = 237;
			this.b = 255;
		}else {
			this.r = 237;
			this.g = 5;
			this.b = 36;
		}
	}
	
	void init(int x, int y, int x2, int y2) {
		this.x = Level.col2X(x)+Config.CURR_T_SIZE/2;
		this.y = Level.row2Y(y)+Config.CURR_T_SIZE/2;
		this.x2 = Level.col2X(x2)+Config.CURR_T_SIZE/2+(rnd.nextInt((14*Config.SIZE_MULT))-7*Config.SIZE_MULT);
		this.y2  = Level.row2Y(y2)+Config.CURR_T_SIZE/2+(rnd.nextInt((14*Config.SIZE_MULT))-7*Config.SIZE_MULT);
		this.transp = 100;
		this.decay = 6;
		this.lastTime = System.currentTimeMillis();
		this.lightUp = true;
		if(Game.soundOn && !AudioGalery.laser.clip.isRunning())
    		AudioGalery.laser.playClip();
		GUIHandler.objects.add(this);
	}

	@Override
	public void update() {
		if(lightUp) {
			if(System.currentTimeMillis() - lastTime > 1) {
				this.transp += decay*3;
				lastTime = System.currentTimeMillis();
			}
			if(this.transp >= 230) {
				this.lightUp = false;
			}
		}
		else {
			if(System.currentTimeMillis() - lastTime > 2) {
				this.transp -= decay;
				lastTime = System.currentTimeMillis();
			}
			if(this.transp <= 0) {
				this.transp = 0;
				this.done = true;
			}
		}
	}
	
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(new Color(this.r, this.g, this.b, transp));
		g2.drawLine(x, y, x2, y2);
		g2.setStroke(new BasicStroke(1));
	}
}
