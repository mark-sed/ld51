package com.sedlacek.ld51.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.GameObject;

public abstract class Entity extends GameObject {

	protected BufferedImage img;
	protected int vision;
	protected int maxHp, hp;
	
	public void render(Graphics g, int x, int y, Tile parent) {
		if(img != null) {
			g.drawImage(img, x, y, Config.CURR_T_SIZE, Config.CURR_T_SIZE, null);
			if(this.maxHp > 0 && this.hp < this.maxHp && !parent.isInFog()) {
				g.setColor(new Color(240,20,20,220));
				int hw = (int)(((float)(Config.CURR_T_SIZE-2*Config.SIZE_MULT)/(float)maxHp)*(float)hp);
				g.fillRect(x+1*Config.SIZE_MULT, y+1*Config.SIZE_MULT, hw, 1*Config.SIZE_MULT);
			}
		}
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
