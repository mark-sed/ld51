package com.sedlacek.ld51.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.entities.AttackFleet;
import com.sedlacek.ld51.entities.EnemyBase;
import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.entities.MotherShip;
import com.sedlacek.ld51.entities.RepairShip;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.entities.Ship.ShipType;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.GameObject.Status;

public class Level {

	public static int W = 15;
	public static int H = 8;
	public Tile[][] map;
	public static int startTileX = 4*Config.SIZE_MULT;
	public static int startTileY = 12*Config.SIZE_MULT;
	
	private BufferedImage bckg;
	
	private Random r = new Random();

	public boolean uncoverFog;
	
	public long lastEventTime, eventDelay = 1000*60*5;
	
	public long start;
	
	public Level() {
		W = 15;
		H = 8;
		map = new Tile[W][H];
		this.generateMap();
		
		bckg = new BufferedImage(Config.WIDTH/Config.SIZE_MULT, Config.HEIGHT/Config.SIZE_MULT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bckg.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,Config.WIDTH, Config.HEIGHT);
		int starAm = r.nextInt(30)+25;
		for(int i = 0; i < starAm; ++i) {
			int w = r.nextInt(1)+1;
			g.setColor(new Color(255,255,255, r.nextInt(200)+55));
			g.fillRect(r.nextInt(Config.WIDTH/Config.SIZE_MULT), r.nextInt(Config.HEIGHT/Config.SIZE_MULT), w, w);
		}
		
		Game.log.setHide(false);
	}
	
	public Level(boolean tutorial) {
		start = System.currentTimeMillis();
		lastEventTime = start;
		W = H = 6;
		map = new Tile[W][H];
		
		for(int x = 0; x < W; ++x) {
			for(int y = 0; y < H; ++y) {
				//if(r.nextInt(5) != 1)
				map[x][y] = new Void(x, y);
			}
		}
		
		map[W-1][0].setOccupier(new PlanetDream(3, 4));
		
		// Place player
		Game.player.motherShip = new MotherShip(0, 3, Tile.Status.FRIENDLY);
		setTile(0, 3, Game.player.motherShip);
		
		// Place enemy
		Game.enemy.base = new EnemyBase(W-1, H-1, Tile.Status.HOSTILE);
		setTile(W-1, H-1, Game.enemy.base);
		
		bckg = new BufferedImage(Config.WIDTH/Config.SIZE_MULT, Config.HEIGHT/Config.SIZE_MULT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bckg.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,Config.WIDTH, Config.HEIGHT);
		int starAm = r.nextInt(30)+25;
		for(int i = 0; i < starAm; ++i) {
			int w = r.nextInt(1)+1;
			g.setColor(new Color(255,255,255, r.nextInt(200)+55));
			g.fillRect(r.nextInt(Config.WIDTH/Config.SIZE_MULT), r.nextInt(Config.HEIGHT/Config.SIZE_MULT), w, w);
		}
	}
	
	public boolean setTile(int col, int row, Entity e) {
		if(this.map[col][row] == null)
			return false;
		map[col][row].setOccupier(e);
		return true;
	}
	
	/*private int[] placePlanet() {
		int pc = 0;
		int pr = 0;
		boolean cannotPlace = false;
		do {
			pc = r.nextInt(W-4)+2;
			pr = r.nextInt(H-4)+2;
			for(int x = pc-1; x <= pc+1 && !cannotPlace; ++x) {
				for(int y = pr-1; y <= pr+1; ++y) {
					if(map[x][y].getOccupier() != null) {
						cannotPlace = true;
						break;
					}
				}
			}
		} while (cannotPlace);
		return new int[] { pc, pr };
	}*/
	
	public void generateMap() {
		start = System.currentTimeMillis();
		lastEventTime = start;
		for(int x = 0; x < W; ++x) {
			for(int y = 0; y < H; ++y) {
				//if(r.nextInt(5) != 1)
				map[x][y] = new Void(x, y);
			}
		}
		// TODO: Remove and generate automatically
		map[r.nextInt(Level.W-1)][0].setStatus(Status.EVENT);
		
		map[3][4].setOccupier(new PlanetDream(3, 4));
		map[12][4].setOccupier(new PlanetHeg(10,3));
		map[7][1].setOccupier(new PlanetEdems(7, 1));
		map[7][6].setOccupier(new PlanetAnik(7,6));
		
		/*
		int[] pcr = placePlanet();
		map[pcr[0]][pcr[1]].setOccupier(new PlanetDream(pcr[0],pcr[1]));
		
		pcr = placePlanet();
		map[pcr[0]][pcr[1]].setOccupier(new PlanetHeg(pcr[0],pcr[1]));

		pcr = placePlanet();
		map[pcr[0]][pcr[1]].setOccupier(new PlanetEdems(pcr[0],pcr[1]));
		
		pcr = placePlanet();
		map[pcr[0]][pcr[1]].setOccupier(new PlanetAnik(pcr[0],pcr[1]));
		*/
		// Place player
		int py = r.nextInt(H-2)+1;
		Game.player.motherShip = new MotherShip(0, py, Tile.Status.FRIENDLY);
		setTile(0, py, Game.player.motherShip);
		
		// Place enemy
		py = r.nextInt(H-2)+1;
		Game.enemy.base = new EnemyBase(W-1, py, Tile.Status.HOSTILE);
		setTile(W-1, py, Game.enemy.base);
		Ship s = new RepairShip(W-1, py+1, Status.HOSTILE);
		Game.enemy.fleet.add(s);
		map[s.getCol()][s.getRow()].setOccupier(s);
		Ship s2 = new AttackFleet(W-1, py-1, Status.HOSTILE);
		Game.enemy.fleet.add(s2);
		map[s2.getCol()][s2.getRow()].setOccupier(s2);
	}
	
	public static int col2X(int col) {
		return startTileX+col*Config.TILE_SIZE*Config.SIZE_MULT+col*2;
	}
	
	public static int row2Y(int row) {
		return startTileY+row*Config.TILE_SIZE*Config.SIZE_MULT+row*2;
	}
	
	public int[] getHighlXY() {
		for(int x = 0; x < W; ++x) {
			for(int y = 0; y < H; ++y) {
				if(map[x][y].isMouseOver()) {
					return new int[] {
							col2X(x),
							row2Y(y),
							x, 
							y
					};
				}
			}
		}
		return null;
	}
	
	public void update() {
		for(int x = 0; x < W; ++x) {
			for(int y = 0; y < H; ++y) {
				map[x][y].update();
				map[x][y].setInFog(true);
				map[x][y].setCanTravel(false);
				map[x][y].setBuildable(false);
				if(Game.player.infoBox.shipSelected && Game.player.infoBox.go != null) {
					if(map[x][y].getOccupier() == null && 
							Game.player.canMove(Game.player.infoBox.go, x, y)) {
						map[x][y].setCanTravel(true);
					}
				}
				else {
					for(int i = x-1; i <= x+1; ++i) {
						if(i < 0 || i >= W) continue;
						for(int j = y-1; j <= y+1; ++j) {
							if(j < 0 || j >= H) continue;
							Entity e = map[i][j].getOccupier();
							if(e != null && e instanceof MotherShip && map[x][y] instanceof Void && map[x][y].getOccupier() == null)
								map[x][y].setBuildable(true);
						}
					}
				}
				
			}
		}
		for(int x = 0; x < W; ++x) {
			for(int y = 0; y < H; ++y) {
				Entity o = map[x][y].getOccupier();
				if(o != null) {
					if(o instanceof Ship && ((Ship)o).getType() == ShipType.SCOUT) {
						// 1 region
						for(int i = x-1; i <= x+1; ++i) {
							if(i < 0 || i >= W) continue;
							for(int j = y-1; j <= y+1; ++j) {
								if(j < 0 || j >= H) continue;
								if(o.getStatus() == Status.FRIENDLY) {
									map[i][j].setInFog(false);
									map[i][j].setSeen(true);
								}
								else {
									if(map[i][j].getStatus() != Status.EVENT)
										map[i][j].setStatus(o.getStatus());
								}
							}
						}
						for(int i = x-o.getVision(); i <= x+o.getVision(); ++i) {
							if(i < 0 || i >= W) continue;
							if(o.getStatus() == Status.FRIENDLY) {
								map[i][y].setInFog(false);
								map[i][y].setSeen(true);
							}
							else {
								if(map[i][y].getStatus() != Status.EVENT)
									map[i][y].setStatus(o.getStatus());
							}
						}
						for(int i = y-o.getVision(); i <= y+o.getVision(); ++i) {
							if(i < 0 || i >= H) continue;
							if(o.getStatus() == Status.FRIENDLY) {
								map[x][i].setInFog(false);
								map[x][i].setSeen(true);
							}
							else {
								if(map[x][i].getStatus() != Status.EVENT)
									map[x][i].setStatus(o.getStatus());
							}
						}
					}
					else {
						for(int i = x-o.getVision(); i <= x+o.getVision(); ++i) {
							if(i < 0 || i >= W) continue;
							for(int j = y-o.getVision(); j <= y+o.getVision(); ++j) {
								if(j < 0 || j >= H) continue;
								if(o.getStatus() == Status.FRIENDLY) {
									map[i][j].setInFog(false);
									map[i][j].setSeen(true);
								}
								else {
									if(map[i][j].getStatus() != Status.EVENT)
										map[i][j].setStatus(o.getStatus());
								}
							}
						}
					}
				}
				if(uncoverFog) {
					map[x][y].setInFog(false);
					map[x][y].setSeen(true);
				}
			}
		}
		
		if(System.currentTimeMillis() - lastEventTime >= eventDelay && !Game.inTutorial) {
			map[r.nextInt(Level.W-1)][r.nextInt(Level.H-1)].setStatus(Status.EVENT);
			lastEventTime = System.currentTimeMillis();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(bckg, 0, 0, Config.WIDTH, Config.HEIGHT, null);
		for(int x = 0; x < W; ++x) {
			for(int y = 0; y < H; ++y) {
				if(map[x][y] != null) {
					map[x][y].render(g, startTileX+x*Config.TILE_SIZE*Config.SIZE_MULT+x*2, startTileY+y*Config.TILE_SIZE*Config.SIZE_MULT+y*2);
				}
			}
		}
	}
	
	
}
