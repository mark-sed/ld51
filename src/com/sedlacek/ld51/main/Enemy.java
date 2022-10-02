package com.sedlacek.ld51.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld51.entities.AttackFleet;
import com.sedlacek.ld51.entities.EnemyBase;
import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.gui.ShipMove;
import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.main.GameObject.Status;

public class Enemy {

	private Player p;
	public ArrayList<Ship> fleet;
	public EnemyBase base;
	public long updateTime, lastDecisionTime, lastBlocadeTime, lastDestroyTime, lastBuildTime;
	
	public boolean initPos;
	private int nextAttackAmount;
	
	private Random r = new Random();
	
	public Enemy(Player p) {
		this.p = p;
		this.fleet = new ArrayList<Ship>();
		this.updateTime = 500;
		this.nextAttackAmount = 4;
	}
	
	public void blocade(int col, int row) {
		for(int i = col-1; i<= col+1; ++i) {
			for(int j = row-1; j<= row+1; ++j) {
				if(j == row && i == col) continue;
				if(Game.level.map[i][j].getOccupier() == null) {
					this.addShip(new AttackFleet(i, j, Status.HOSTILE));
					Game.level.map[i][j].getOccupier().setStatus(Status.HOSTILE);
					Game.level.map[i][j].setStatus(Status.HOSTILE);
				}
			}
		}
		this.lastBuildTime = this.lastBlocadeTime = System.currentTimeMillis();
	}
	
	public boolean isBlocadeDamaged(int col, int row) {
		int shipAm = 0;
		for(int i = col-1; i<= col+1; ++i) {
			for(int j = row-1; j<= row+1; ++j) {
				if(j == row && i == col) continue;
				shipAm+=Game.level.map[i][j].getOccupier() != null ? 1 : 0;
			}
		}
		return !(shipAm == 0 || shipAm == 8);
	}
	
	private int[] getAttackPos() {
		ArrayList<Tile> possible = new ArrayList<Tile>();
		int col = p.motherShip.getCol();
		int row = p.motherShip.getRow();
		for(int i = col-2; i<= col+2; ++i) {
			if(i < 0 || i >= Level.W) continue;
			for(int j = row-2; j<= row+2; ++j) {
				if(j == row && i == col) continue;
				if(j < 0 || j >= Level.H) continue;
				Entity e = Game.level.map[i][j].getOccupier();
				if(e == null) {
					possible.add(Game.level.map[i][j]);
				}
			}
		}
		if(possible.size() > 0) {
			Tile t = possible.get(r.nextInt(possible.size()));
			return new int[] { t.getCol(), t.getRow() };
		}
		// All occupied, destroy one
		for(int i = col-2; i<= col+2; ++i) {
			if(i < 0 || i >= Level.W) continue;
			for(int j = row-2; j<= row+2; ++j) {
				if(j == row && i == col) continue;
				if(j < 0 || j >= Level.H) continue;
				Entity e = Game.level.map[i][j].getOccupier();
				if(e != null && e instanceof Planet) {
					possible.add(Game.level.map[i][j]);
				}
			}
		}
		if(possible.size() > 0) {
			Tile t = possible.get(r.nextInt(possible.size()));
			return new int[] { t.getCol(), t.getRow() };
		}
		return new int[] {0, 0};
	}
	
	public void update() {
		if(!Game.inTutorial) {
			long now = System.currentTimeMillis();
			if(System.currentTimeMillis() - lastDecisionTime >= this.updateTime) {
				if(!initPos) {
					blocade(7,6);
					initPos = true;
				}
				else if(isBlocadeDamaged(7,6) && r.nextInt(10) == 1
							&& System.currentTimeMillis() - lastBlocadeTime >= 60000
							&& System.currentTimeMillis() - lastDestroyTime >= 15000) {
					blocade(7,6);
					initPos = true;
				}
				else if(now - lastBuildTime >= 60000) {
					int tc = base.getCol();
					int tr = base.getRow()-1;
					boolean defense = false;
					if(r.nextBoolean() ) {
						// Defense stack
						defense = true;
						tc -= 1;
						tr += 1;
					}
					boolean built = false;
					Entity ab = Game.level.map[tc][tr].getOccupier();
					if(ab != null && ab instanceof AttackFleet) {
						Ship s = (Ship)ab;
						built = true;
						s.setStacks(s.getStacks()+1+(p.level > 1 ? 1 : 0));
						if(s.getStacks() > nextAttackAmount && !defense) {
							int[] xy = getAttackPos();
							new ShipMove(s, xy[0], xy[1]);
							nextAttackAmount*=2;
						}
					}
					if(!built) {
						addShip(new AttackFleet(tc, tr, Status.HOSTILE));
					}
					lastBuildTime = now;
				}
				this.lastDecisionTime = System.currentTimeMillis();
			}
		}
		
		if(base.getStacks() <= 0) {
			Game.game.gameover(true);
		}
		
		for(int i = 0; i < fleet.size(); ++i) {
			if(fleet.get(i).getStacks() <= 0) {
				Game.level.map[fleet.get(i).getCol()][fleet.get(i).getRow()].setOccupier(null);
				fleet.remove(i);
				lastDestroyTime = System.currentTimeMillis();
				if(Game.soundOn)
		    		AudioGalery.explosion.playClip();
			}
		}
	}
	
	public void render(Graphics g) {
		
	}

	public void addShip(Ship s) {
		fleet.add(s);
		Game.level.map[s.getCol()][s.getRow()].setOccupier(s);
	}
}
