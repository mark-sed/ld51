package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sedlacek.ld51.main.Config;

public class EventLog extends GUIObject {

	private int logAm;
	private String[] logs;
	private long lastTime, stayTime;
	
	public EventLog() {
		this.x = 2*Config.SIZE_MULT;
		this.y = 11*Config.SIZE_MULT;
		this.w = 0;
		this.h = 0;
		this.logAm = 5;
		this.stayTime = 3000;
		
		logs = new String[5];
		GUIHandler.objects.add(this);
	}
	
	public void add(String msg) {
		for(int i = logAm-1; i > 0; --i) {
			logs[i] = logs[i-1];
		}
		logs[0] = msg;
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void update() {
		if(System.currentTimeMillis() - lastTime >= stayTime) {
			for(int i = logAm-1; i > 0; --i) {
				logs[i] = logs[i-1];
			}
			logs[0] = null;
			lastTime = System.currentTimeMillis();
		}
	}
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setFont(new Font("DorFont02", Font.PLAIN, 6*Config.SIZE_MULT));
		g.setColor(new Color(200,200,200,255));
		int sp = 4*Config.SIZE_MULT;
		for(int i = 0; i < logAm; ++i) {
			if(logs[i] != null) {
				g.drawString(logs[i], x, y+logAm*sp-i*sp);
			}
		}
	}
}
