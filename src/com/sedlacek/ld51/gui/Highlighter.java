package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Graphics;

public class Highlighter extends GUIObject {

	private int transp;
	private int decay;
	private boolean lightUp;
	private int r, g, b;
	private long lastTime;
	
	public Highlighter(int x, int y, int w, int h, boolean good) {
		init(x, y, w, h);
		if(!good) {
			this.r = 255;
			this.g = 36;
			this.b = 36;
		}else {
			this.r = 36;
			this.g = 255;
			this.b = 36;
		}
	}
	
	void init(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.transp = 100;
		this.decay = 4;
		this.lastTime = System.currentTimeMillis();
		this.lightUp = true;
		GUIHandler.objects.add(this);
	}

	@Override
	public void update() {
		if(lightUp) {
			if(System.currentTimeMillis() - lastTime > 2) {
				this.transp += decay*3;
				lastTime = System.currentTimeMillis();
			}
			if(this.transp >= 230) {
				this.lightUp = false;
			}
		}
		else {
			if(System.currentTimeMillis() - lastTime > 10) {
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
		g.setColor(new Color(this.r, this.g, this.b, transp));
		g.fillRect(x, y, w, h);
	}
}
