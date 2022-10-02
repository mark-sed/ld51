package com.sedlacek.ld51.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public class RepairShip extends Ship {
	
	private Entity healTarget;
	private long lastTime;
	private int delay, healAm;

	public RepairShip(int col, int row, Status stat) {
		super(col, row, stat);
		this.type = ShipType.SUPPORT;
		this.name = "Repair ship";
		this.desc = new String[] {
			"Autorepairs ships within 1 tile",
			"region."
		};
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/ships.png"));
		
		this.icon = this.img = ss.grabImage(1, 2, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		
		this.vision = 1;
		this.speed = 1;
		
		this.maxHp = 250;
		this.hp = this.maxHp;
		healTarget = null;
		
		this.delay = 500;
		this.healAm = 1;
	}

	@Override
	public void render(Graphics g, int x, int y, Tile parent)  {
		super.render(g,x,y,parent);
		if(healTarget != null) {
			if(healTarget.getHp() >= healTarget.getMaxHp()) {
				healTarget = null;
			}
			else {
				g.setColor(new Color(22,230,22));
				g.drawLine(x+Config.CURR_T_SIZE/2, y+Config.CURR_T_SIZE/2, healTarget.getX()+Config.CURR_T_SIZE/2, healTarget.getY()+Config.CURR_T_SIZE/2);
				
				if(System.currentTimeMillis() - lastTime >= (long)((float)(this.delay)*Game.player.repairDelayMult)) {
					if(healTarget.getHp()+healAm > healTarget.getMaxHp()) {
						healTarget.setHp(healTarget.getMaxHp());
					}	
					else {
						healTarget.setHp(healTarget.getHp()+healAm);
					}
					lastTime = System.currentTimeMillis();
				}
			}
		}
		else {
			for(int x1 = col-1; x1 <= col+1; x1++) {
				for(int y1 = row-1; y1 <= row+1; y1++) {
					if(x1 >= 0 && x1 < Level.W && y1 >= 0 && y1 < Level.H) {
						Entity e = Game.level.map[x1][y1].getOccupier();
						if(e != null && e.getStatus()==status && e.getHp() < e.getMaxHp()) {
							healTarget = e;
						}
					}
				}
			}
		}
	}
}
