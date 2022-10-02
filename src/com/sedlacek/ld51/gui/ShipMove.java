package com.sedlacek.ld51.gui;

import java.awt.Graphics;

import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.entities.ShipDestination;
import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.main.AudioGalery;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public class ShipMove extends GUIObject {

	private Ship s;
	private int col, row;
	private int finishX, finishY;
	private long lastTime;
	
	public ShipMove(Ship s, int col, int row) {
		this.s = s;
		this.col = col;
		this.row = row;
		this.x = s.getX();
		this.y = s.getY();
		this.col = col;
		this.row = row;
		this.finishX = Level.col2X(col);
		this.finishY = Level.row2Y(row);
		this.lastTime = System.currentTimeMillis();
		Game.level.map[s.getCol()][s.getRow()].setOccupier(null);
		Game.level.map[col][row].setOccupier(new ShipDestination(s.getStatus()));
		if(Game.soundOn && s.getStatus() == Status.FRIENDLY)
    		AudioGalery.flight.playClip();
		GUIHandler.objects.add(this);
	}
	
	@Override
	public void update() {
		int sp = (int)(s.getSpeed()*Game.player.speedMult);
		if(System.currentTimeMillis() - lastTime >= 100) {
			if(Math.abs(this.x - finishX) > sp) {
				if(finishX > x) {
					this.x += sp;		
				}
				else {
					this.x -= sp;
				}
			}
			if(Math.abs(this.y - finishY) > sp) {
				if(finishY > y) {
					this.y += sp;	
				}
				else {
					this.y -= sp;
				}
			}		
			lastTime =System.currentTimeMillis();
		}
		if(Math.abs(this.x - finishX) <= 10 && Math.abs(this.y - finishY) <= 10) {
			Game.level.map[col][row].setOccupier(s);
			this.done = true;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(s.getImg(), x, y, Config.CURR_T_SIZE, Config.CURR_T_SIZE, null);
	}
}
