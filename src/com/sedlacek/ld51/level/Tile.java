package com.sedlacek.ld51.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.main.AudioGalery;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.GameObject;
import com.sedlacek.ld51.market.Offer;

public abstract class Tile extends GameObject {

	protected BufferedImage img;
	protected int col, row;
	protected Entity occupier;
	protected boolean inFog, canTravel;
	protected boolean buildable;
	protected boolean seen;
	
	public static Tile selected;
	
	public static Color getStatusColor(Status s) {
		switch(s) {
		case NEUTRAL: return new Color(222, 198, 98);
		case HOSTILE: return new Color(173, 40, 19);
		case FRIENDLY: return new Color(96, 181, 65);
		case UNKNOWN: return new Color(140, 140, 140);
		case EVENT: return new Color(111, 24, 186);
		default:
			System.out.println("Error: Unknown status color.");
			return Color.WHITE;
		}
	}
	
	public Tile(int col, int row) {
		this.col = col;
		this.row = row;
		this.x = Level.startTileX+Config.TILE_SIZE*Config.SIZE_MULT*col;
		this.y = Level.startTileY+Config.TILE_SIZE*Config.SIZE_MULT*row;
		this.w = Config.TILE_SIZE*Config.SIZE_MULT;
		this.h = this.w;
		this.status = Status.NEUTRAL;
		this.seen = false;
	}
	
	@Override
	public void clicked() {
		if(occupier != null && !inFog) {
			Game.player.infoBox.setObject(occupier);
		}
		else {
			Game.player.infoBox.setObject(this);
		}
		if(selected != this)
			selected = this;
		else
			selected = null;
	}
	
	@Override
	public void update() {
		super.updateMouse();
		if(occupier != null) {
			occupier.setX(x);
			occupier.setY(y);
			occupier.setCol(col);
			occupier.setRow(row);
			occupier.update();
			this.status = occupier.getStatus();
		}
		if(status == null) {
			status = Status.NEUTRAL;
		}
		/*else if(occupier instanceof Ship) {
			for(int i = col-1; i <= col+1; ++i) {
				if(i < 0 || i >= Level.W) continue;
				for(int j = row-1; j <= row+1; ++j) {
					if(j < 0 || j >= Level.H) continue;
					if(Game.level.map[i][j].getOccupier() == null) {
						Game.level.map[i][j].setStatus(occupier.getStatus());
					}
				}
			}
		}*/
	}
	
	public void render(Graphics g, int x, int y) {
		// TODO: Try tilting the image to make it look not as a top down view
		if(selected == this) {
			g.setColor(new Color(177, 119, 186, 150));
			g.fillRect(x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
		}
		else if(buildable) {
			g.setColor(new Color(29, 32, 194, 150));
			g.fillRect(x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
		}
		if(this.isMouseOver()) {
			g.setColor(new Color(255,255,255,80));
			g.fillRect(x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
		}
		if(img != null)
			g.drawImage(img, x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT, null);
		
		if(this.inFog) {
			g.setColor(getStatusColor(Status.UNKNOWN));
		} else {
			g.setColor(getStatusColor(status));
		}
		g.drawRect(x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
		if(status == Status.EVENT) {
			g.setColor(getStatusColor(status));
			g.setFont(new Font("DorFont03", Font.BOLD, 8*Config.SIZE_MULT));
			g.drawString("?", x+w/2-g.getFontMetrics().stringWidth("?")/2+1*Config.SIZE_MULT, y+h/2+8*Config.SIZE_MULT/2);
		}
		
		if(occupier != null && seen) {
			occupier.render(g, x, y, this);
		}
		else if(!seen) {
			g.drawImage(Offer.iconFog, x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT, null);
		}
		
		if(this.inFog) {
			g.setColor(new Color(0,0,0,180));
			g.fillRect(x, y, Config.TILE_SIZE*Config.SIZE_MULT, Config.TILE_SIZE*Config.SIZE_MULT);
		}
		if(canTravel) {
			g.setColor(new Color(152, 255, 105, 255));
			g.drawRect(x+2, y+2, Config.TILE_SIZE*Config.SIZE_MULT-4, Config.TILE_SIZE*Config.SIZE_MULT-4);
		}
	}
	
	@Override
	public void render(Graphics g) {

	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Entity getOccupier() {
		return occupier;
	}

	public void setOccupier(Entity occupier) {
		// TODO:
		if(this.status == Status.EVENT) {
			Game.player.opportBar.triggerEvent();
			if(Game.soundOn)
				AudioGalery.eventCard.playClip();
			this.status = Status.NEUTRAL;
		}
		this.occupier = occupier;
		if(occupier != null)
			this.status = occupier.getStatus();
	}

	public boolean isInFog() {
		return inFog;
	}

	public void setInFog(boolean inFog) {
		this.inFog = inFog;
	}

	public boolean isCanTravel() {
		return canTravel;
	}

	public void setCanTravel(boolean canTravel) {
		this.canTravel = canTravel;
	}

	public static Tile getSelected() {
		return selected;
	}

	public static void setSelected(Tile selected) {
		Tile.selected = selected;
	}

	public boolean isBuildable() {
		return buildable;
	}

	public void setBuildable(boolean buildable) {
		this.buildable = buildable;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	
}
