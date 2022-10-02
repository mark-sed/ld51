package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.KeyManager;

public class TutorialText extends GUIObject {

	private String header, text;
	private long timeoutms;
	private long startTime;
	
	public TutorialText(Entity e, int x, int y, String header, String text, long timeoutms) {
		this.x = x;
		this.y = y;
		this.header = header;
		this.text = text;
		this.timeoutms = timeoutms;
		this.startTime = System.currentTimeMillis();
		GUIHandler.objects.add(this);
	}

	@Override
	public void update() {
		if(System.currentTimeMillis() - startTime >= timeoutms) {
			this.done = true;
		}
		if(Game.getKeyManager().keys[KeyManager.ENTER]){
			this.done = true;
			Game.getKeyManager().keys[KeyManager.ENTER] = false;
		}
		if(Game.getMouseManager().LClicked){
			this.done = true;
			Game.getMouseManager().LClicked = false;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(40,40,40,200));
		g.setFont(new Font("DorFont02", Font.PLAIN, 8*Config.SIZE_MULT));
		int w = g.getFontMetrics().stringWidth(text)+20;
		g.setFont(new Font("DorFont02", Font.BOLD, 8*Config.SIZE_MULT));
		int hw = g.getFontMetrics().stringWidth(header)+20;
		int mw = hw > w ? hw : w;
		g.fillRect(x, y, mw, 8*Config.SIZE_MULT*2+20);
		g.setColor(new Color(210,210,210,255));
		g.drawString(header, x+2*Config.SIZE_MULT, y+8*Config.SIZE_MULT);
		g.setFont(new Font("DorFont02", Font.PLAIN, 8*Config.SIZE_MULT));
		g.setColor(new Color(250,250,250,255));
		g.drawString(text, x+2*Config.SIZE_MULT, y+8*Config.SIZE_MULT*2+5);
	}
}
