package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld51.entities.AttackFleet;
import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.entities.MotherShip;
import com.sedlacek.ld51.entities.RepairShip;
import com.sedlacek.ld51.entities.Scout;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.entities.Ship.ShipType;
import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.level.Fog;
import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.GameObject;

public class InfoBox extends GUIObject {

	public GameObject go;
	
	private BufferedImage img;
	
	public boolean shipSelected;
	
	private Button colonizeB;
	
	private Button buildFleetB, buildRepairShipB, buildScoutB;
	
	public InfoBox() {
		this.img = ImageLoader.loadNS("/infobox.png");
		this.w = img.getWidth()*Config.SIZE_MULT;
		this.h = img.getHeight()*Config.SIZE_MULT;
		try {
			colonizeB = new Button(new String[]{"Colonize","(-2 fleets)"}, 65*Config.SIZE_MULT, Config.HEIGHT-h+32+5*Config.SIZE_MULT, 30*Config.SIZE_MULT, 12*Config.SIZE_MULT, new Color(73,76,81), Color.WHITE, new Font("DorFont02", Font.PLAIN, 7*Config.SIZE_MULT), this.getClass().getDeclaredMethod("colonizeClicked"), this);
			colonizeB.setYOffset(-5*Config.SIZE_MULT);
			buildFleetB = new Button(
					new String[]{"Build Fleet","(-5 people)"}, 
					7*Config.SIZE_MULT,
					Config.HEIGHT-h+32+23*Config.SIZE_MULT, 
					23*Config.SIZE_MULT, 
					20*Config.SIZE_MULT, 
					new Color(73,76,81), 
					Color.WHITE, 
					new Font("DorFont02", Font.PLAIN, 6*Config.SIZE_MULT), 
					this.getClass().getDeclaredMethod("buildFleetClicked"), 
					this);
			buildFleetB.setYOffset(-8*Config.SIZE_MULT);
			
			buildRepairShipB = new Button(
					new String[]{"Build Repair","ship","(-10 people)"}, 
					7*Config.SIZE_MULT+24*Config.SIZE_MULT,
					Config.HEIGHT-h+32+23*Config.SIZE_MULT, 
					23*Config.SIZE_MULT, 
					20*Config.SIZE_MULT, 
					new Color(73,76,81), 
					Color.WHITE, 
					new Font("DorFont02", Font.PLAIN, 6*Config.SIZE_MULT), 
					this.getClass().getDeclaredMethod("buildRepairClicked"), 
					this);
			buildRepairShipB.setYOffset(-10*Config.SIZE_MULT);
			
			buildScoutB = new Button(
					new String[]{"Build Scout","(-2 people)"}, 
					7*Config.SIZE_MULT+24*Config.SIZE_MULT*2,
					Config.HEIGHT-h+32+23*Config.SIZE_MULT, 
					23*Config.SIZE_MULT, 
					20*Config.SIZE_MULT, 
					new Color(73,76,81), 
					Color.WHITE, 
					new Font("DorFont02", Font.PLAIN, 6*Config.SIZE_MULT), 
					this.getClass().getDeclaredMethod("buildScoutClicked"), 
					this);
			buildScoutB.setYOffset(-10*Config.SIZE_MULT);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buildFleetClicked() {
		Game.player.takePpl(5);
		Ship s = new AttackFleet(go.getCol(), go.getRow(), Status.FRIENDLY);
		Game.player.addShip(s);
		Game.log.add(s.getName()+" has been built.");
	}
	
	public void buildRepairClicked() {
		Game.player.takePpl(10);
		Ship s = new RepairShip(go.getCol(), go.getRow(), Status.FRIENDLY);
		Game.player.addShip(s);
		Game.log.add(s.getName()+" has been built.");
	}
	
	public void buildScoutClicked() {
		Game.player.takePpl(2);
		Ship s = new Scout(go.getCol(), go.getRow(), Status.FRIENDLY);
		Game.player.addShip(s);
		Game.log.add(s.getName()+" has been built.");
	}
	
	public boolean canBuildFleet() {
		return Game.player.getPpl() >= 5;
	}
	
	public boolean canBuildScout() {
		return Game.player.getPpl() >= 2;
	}
	
	public boolean canBuildRepair() {
		return Game.player.getPpl() >= 10;
	}
	
	public void unselect() {
		this.go = null;
		this.shipSelected = false;
	}
	
	public void setObject(GameObject go) {
		if(go == this.go) {
			unselect();
			return;
		}
		// Moving
		if(shipSelected && go instanceof Tile) {
			if(Game.player.canMove(this.go, go.getCol(), go.getRow())) {
				Game.player.move(this.go, go.getCol(), go.getRow());
			}
			else {
				unselect();
				Game.player.statsBar.highlightFuel(false);
				return;
			}
		}
		this.go = go;
	}
	
	@Override
	public void update() {
		if(go != null) {
			if(go instanceof Ship && go.getStatus() == Status.FRIENDLY) {
				shipSelected = true;
			}
			else {
				shipSelected=false;
			}
		}
		else {
			shipSelected = false;
		}
		if(go != null && go instanceof Planet) {
			if(!canColonize()) {
				colonizeB.disabled = true;
			}
			else {
				colonizeB.disabled = false;
			}
			
			if(go.getStatus() != Status.FRIENDLY) {
				colonizeB.update();
			}
		}
		if(go instanceof Tile && ((Tile)go).isBuildable()) {
			if(!canBuildFleet())
				buildFleetB.disabled = true;
			else 
				buildFleetB.disabled = false;
			buildFleetB.update();
			
			if(!canBuildRepair())
				buildRepairShipB.disabled = true;
			else 
				buildRepairShipB.disabled = false;
			buildRepairShipB.update();
			
			if(!canBuildScout())
				buildScoutB.disabled = true;
			else 
				buildScoutB.disabled = false;
			buildScoutB.update();
		}
	}
	
	public boolean canColonize() {
		if(go == null || !(go instanceof Planet) || ((Planet)go).getStatus() == Status.FRIENDLY) return false;
		for(int i = go.getCol()-1; i <= go.getCol()+1; ++i) {
			for(int j = go.getRow()-1; j <= go.getRow()+1; ++j) {
				if(i < 0 || i >= Level.W || j < 0 || j >= Level.H) continue;
				Entity oc = Game.level.map[i][j].getOccupier();
				if(oc instanceof Ship && ((Ship)oc).getType() == ShipType.ATTACK && ((Ship)oc).getStacks() >= 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void colonizeClicked() {
		if(canColonize()) {
			for(int i = go.getCol()-1; i <= go.getCol()+1; ++i) {
				for(int j = go.getRow()-1; j <= go.getRow()+1; ++j) {
					if(i < 0 || i >= Level.W || j < 0 || j >= Level.H) continue;
					Entity oc = Game.level.map[i][j].getOccupier();
					if(oc instanceof Ship && ((Ship)oc).getType() == ShipType.ATTACK && ((Ship)oc).getStacks() >= 2 && ((Ship)oc).getStatus() == Status.FRIENDLY) {
						((Ship)oc).setStacks(((Ship)oc).getStacks()-2);
						Planet p = (Planet)go;
						p.setStatus(Status.FRIENDLY);
						Game.player.colonies.add(p);
						if(Game.player.opportBar.opports.size() < 4 && Game.player.colonizationsDone < 1) {
							Game.player.opportBar.createCard();
						}
						Game.player.colonizationsDone+=1;
						Game.log.add("You have colonized "+p.getName()+".");
					}
				}
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		int sy = Config.HEIGHT-h+32;
		int sx = 4*Config.SIZE_MULT;
		if(go != null && go instanceof Ship) {
			Ship e = (Ship)go;
			if(e.getStatus() == Status.HOSTILE) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLUE);
			}
			g.drawRect(Level.col2X(e.getCol()-e.getVision())+Config.CURR_T_SIZE/2, Level.row2Y(go.getRow()-e.getVision())+Config.CURR_T_SIZE/2,
					e.getVision()*2*Config.CURR_T_SIZE, e.getVision()*2*Config.CURR_T_SIZE);
		}
		
		g.drawImage(img, sx, sy, w, h, null);
		GameObject oldgo = go;
		if(go != null) {
			if(go instanceof Tile && ((Tile)go).isInFog()) {
				go = new Fog();
			}
			
			if(go.getIcon() != null) {
				g.drawImage(go.getIcon(), sx+4*Config.SIZE_MULT, sy+4*Config.SIZE_MULT, 16*Config.SIZE_MULT, 16*Config.SIZE_MULT, null);
			}
			
			g.setFont(new Font("DorFont02", Font.BOLD, 8*Config.SIZE_MULT));
			g.setColor(Color.WHITE);
			if(go.getName() != null) {
				if(go instanceof MotherShip) {
					g.drawString(go.getName()+" (level "+Game.player.level+")", sx+23*Config.SIZE_MULT, sy+9*Config.SIZE_MULT);
				}
				else {
					g.drawString(go.getName(), sx+23*Config.SIZE_MULT, sy+9*Config.SIZE_MULT);
				}
			}
			else {
				g.drawString("???", sx+23*Config.SIZE_MULT, sy+9*Config.SIZE_MULT);
			}
			g.setFont(new Font("DorFont02", Font.PLAIN, 7*Config.SIZE_MULT));
			g.setColor(new Color(200,200,200));
			if(go instanceof Tile && ((Tile)go).isBuildable()) {
				g.drawString("Build area", sx+23*Config.SIZE_MULT, sy+14*Config.SIZE_MULT);
			}
			else {
				g.drawString("Status: "+GameObject.getStatusName(go.getStatus()), sx+23*Config.SIZE_MULT, sy+14*Config.SIZE_MULT);
			}
			
			if(go instanceof Entity) {
				Entity e = (Entity)go;
				if(e.getMaxHp() > 0) {
					g.setColor(new Color(200,20,20));
					g.drawString("HP: "+e.getHp()+"/"+e.getMaxHp(), sx+23*Config.SIZE_MULT, sy+(19)*Config.SIZE_MULT);
				}
				else if(e instanceof Planet && e.getStatus() == Status.FRIENDLY) {
					g.setColor(new Color(200,20,20));
					g.drawString("Happiness: "+((Planet)e).getHappiness()+"/"+((Planet)e).getMaxHappiness(), sx+23*Config.SIZE_MULT, sy+(19)*Config.SIZE_MULT);
				}
			}
			
			if(go instanceof MotherShip) {
				g.setFont(new Font("DorFont01", Font.BOLD, 6*Config.SIZE_MULT));
				g.setColor(Color.WHITE);
				
				int mny = Game.player.passiveIncome + Game.player.extraIncome + Game.player.getPpl()/5;
				String pplS = (Game.player.level < 2) ? "0-1" : "1";
				int am = (int)((Game.player.getPpl()/3)*Game.player.foodMult);
				am = am == 0 ? 1 : am;
				am -= Game.player.farming;
				String amS = am > 0 ? "+"+am : ""+am;
				String[] dsc = new String[] {
					"+"+mny+" gold.",
					"+"+pplS+" people.",
					amS
				};
				
				sy += 22*Config.SIZE_MULT+5*Config.SIZE_MULT;
				for(int i = 0; i < dsc.length; ++i) {
					g.drawString(dsc[i], sx+4*Config.SIZE_MULT, sy);
					sy+=5*Config.SIZE_MULT;
				}
			}
			else if(go.getDesc() != null) {
				g.setFont(new Font("DorFont01", Font.BOLD, 6*Config.SIZE_MULT));
				g.setColor(Color.WHITE);
				
				sy += 22*Config.SIZE_MULT+5*Config.SIZE_MULT;
				for(int i = 0; i < go.getDesc().length; ++i) {
					g.drawString(go.getDesc()[i], sx+4*Config.SIZE_MULT, sy);
					sy+=5*Config.SIZE_MULT;
				}
			}
			
			if(shipSelected) {
				g.setColor(Color.RED);
				int[] xy = Game.level.getHighlXY();
				if(xy != null) {
					g.drawLine(Level.col2X(go.getCol())+Config.TILE_SIZE*Config.SIZE_MULT/2, Level.row2Y(go.getRow())+Config.TILE_SIZE*Config.SIZE_MULT/2, 
							xy[0]+Config.TILE_SIZE*Config.SIZE_MULT/2, xy[1]+Config.TILE_SIZE*Config.SIZE_MULT/2);
					g.fillOval(xy[0]+Config.TILE_SIZE*Config.SIZE_MULT/2-Config.SIZE_MULT, xy[1]+Config.TILE_SIZE*Config.SIZE_MULT/2-Config.SIZE_MULT, 2*Config.SIZE_MULT, 2*Config.SIZE_MULT);
				}
			}
		}
		this.go = oldgo;
		if(go instanceof Planet) {
			if(go.getStatus() != Status.FRIENDLY) {
				colonizeB.render(g);
			}
		}
		if(go instanceof Tile && ((Tile)go).isBuildable()) {
			buildFleetB.render(g );
			buildRepairShipB.render(g);
			buildScoutB.render(g);
		}
	}

}
