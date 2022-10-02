package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Graphics;

public class Timer {
	
	private long s;
	private long maxS;
	private long lastTime;
	public boolean finished;
	private Color c;
	
	public Timer(int s, Color c) {
		this.maxS = this.s = s*1000;
		this.lastTime = System.currentTimeMillis();
		this.finished = false;
		this.c = c;
	}
	
	public Timer() {
		this.maxS = this.s = 10*1000;
		this.lastTime = System.currentTimeMillis();
		this.finished = false;
		this.c = new Color(179, 51, 37);
		//this.c = new Color(122, 235, 225);
	}
	
	public void update() {
		// Not precise, but does not require start and stop methods
		if(System.currentTimeMillis() - lastTime >= 100) {
			s-=100;
			this.lastTime = System.currentTimeMillis();
		}
		if(s <= 0) {
			this.finished = true;
		}
	}
	
	public void render(Graphics g, int x, int y, int maxW, int h, boolean left2Right) {
		if(s <= 0) return;
		g.setColor(c);
		int w = (int)(((float)maxW/(float)maxS)*(float)s);
		if(left2Right)
			g.fillRect(x-w+maxW, y, w, h);
		else
			g.fillRect(x, y, w, h);
	}

	public long getS() {
		return s;
	}

	public void setS(long s) {
		this.s = s;
	}

	public long getMaxS() {
		return maxS;
	}

	public void setMaxS(long maxS) {
		this.maxS = maxS;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	
	
}
