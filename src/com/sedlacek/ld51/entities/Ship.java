package com.sedlacek.ld51.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import com.sedlacek.ld51.gui.Laser;
import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public abstract class Ship extends Entity {

	public static enum ShipType {
		ATTACK,
		BASE,
		SUPPORT,
		SCOUT
	}
	
	public static enum ShipState {
		IDLE,
		MOVING
	}
	
	protected ShipType type;
	protected ShipState state;
	protected int speed;
	protected int stacks;
	protected Ship target;
	protected long attackSpeed;
	protected int dmg;
	protected long lastAttackTime;
	private static Random r = new Random();
	
	public Ship(int col, int row, Status stat) {
		this.col = col;
		this.row = row;
		this.status = stat;
		this.h = this.w = 16*Config.TILE_SIZE;
		this.state = ShipState.IDLE;
		this.stacks = 1;
	}
	
	@Override
	public void update() {
		if(this.hp <= 0) {
			this.hp += maxHp;
			this.stacks -= 1;
		}
		if(Game.inTutorial && type == ShipType.BASE) return;
		if(type == ShipType.BASE || type == ShipType.ATTACK) {
			this.target = null;
			for(int i = col-vision; i <= col+vision; ++i) {
				if(i < 0 || i >= Level.W) continue;
				for(int j = row-vision; j <= row+vision; ++j) {
					if(j < 0 || j >= Level.H) continue;
					Entity e = Game.level.map[i][j].getOccupier();
					if(e != null && e instanceof Ship) {
						Ship s = (Ship)e;
						if(s.getStatus() != status) {
							this.target = s;
						}
					}
				}
			}
				
		}
		
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	public void render(Graphics g, int x, int y, Tile parent) {
		super.render(g, x, y, parent);
		if(Game.state != Game.State.GAME) return;
		if(stacks > 1) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("DorFont02", Font.PLAIN, 6*Config.SIZE_MULT));
			g.fillRect(x+Config.CURR_T_SIZE-g.getFontMetrics().stringWidth(""+stacks)-2*Config.SIZE_MULT, y+Config.CURR_T_SIZE-5*Config.SIZE_MULT, 
					g.getFontMetrics().stringWidth(""+stacks)+2*Config.SIZE_MULT, 5*Config.SIZE_MULT);
			g.setColor(Color.WHITE);
			g.drawString(""+stacks, x+Config.CURR_T_SIZE-g.getFontMetrics().stringWidth(""+stacks)-1*Config.SIZE_MULT, y+Config.CURR_T_SIZE-1*Config.SIZE_MULT);
		}
		
		if(this.target != null && System.currentTimeMillis() - lastAttackTime >= this.attackSpeed) {
			if(this.status == Status.FRIENDLY) {
				if(Game.player.getAmmo() > 0) {
					int d = stacks;
					if(Game.player.getAmmo() < stacks) {
						d = Game.player.getAmmo();
					}
					new Laser(col, row, target.getCol(), target.getRow(), status==Status.FRIENDLY);
					target.setHp(target.getHp()-dmg*d+(r.nextInt(4)-2));
					Game.player.takeAmmo(d);
				}
				else {
					Game.player.statsBar.highlightAmmo(false);
					Game.log.add("Your ships require ammo!");
				}
			}
			else {
				new Laser(col, row, target.getCol(), target.getRow(), status==Status.FRIENDLY);
				target.setHp(target.getHp()-dmg*stacks+(r.nextInt(4)-2));
			}
			this.lastAttackTime = System.currentTimeMillis();
		}
	}

	public ShipType getType() {
		return type;
	}

	public void setType(ShipType type) {
		this.type = type;
	}

	public ShipState getState() {
		return state;
	}

	public void setState(ShipState state) {
		this.state = state;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getStacks() {
		return stacks;
	}

	public void setStacks(int stacks) {
		this.stacks = stacks;
	}

	public Ship getTarget() {
		return target;
	}

	public void setTarget(Ship target) {
		this.target = target;
	}

	public long getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(long attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public long getLastAttackTime() {
		return lastAttackTime;
	}

	public void setLastAttackTime(long lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}
	
	
}
