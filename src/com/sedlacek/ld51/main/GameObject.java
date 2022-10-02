package com.sedlacek.ld51.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	
	public static enum Status {
		NEUTRAL,
		HOSTILE,
		FRIENDLY,
		UNKNOWN,
		EVENT
	}
	
	public static String getStatusName(Status s) {
		if(s == null) {
			return "???";
		}
		switch(s) {
			case NEUTRAL: return "Neutral";
			case HOSTILE: return "Hostile";
			case FRIENDLY: return "Friendly";
			case UNKNOWN: return "Unknown";
			case EVENT: return "Event";
		}
		return "???";
	}
	
	protected Status status;
	
	protected String name;
	protected String[] desc;
	
	protected BufferedImage icon;
	protected boolean disableClick;
	
	protected int x,
	  y,
	  w,
	  h,
	  col,
	  row;

	public void updateMouse() {
		if(Game.getMouseRect().intersects(new Rectangle(x, y, w, h))){
            if(Game.getMouseManager().LClicked){
            	if(!disableClick) {
            		this.clicked();
            	}
                Game.getMouseManager().LClicked = false;
            }
        }
	}
	public abstract void update();
	public abstract void render(Graphics g);
	public void fixedUpdate(){}
	public void clicked(){
		Game.player.infoBox.setObject(this);
	};
	protected CollisionBox collisionBox;
	
	protected GameObject(){

	}
	
	public boolean isMouseOver() {
		if(new Rectangle(x, y, w, h).intersects(Game.getMouseRect())) {
			return true;
		}
		return false;
	}
	
	//Custom setters
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//Getters and setters
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int width) {
		this.w = width;
	}
	public int getH() {
		return h;
	}
	public void setH(int height) {
		this.h = height;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
	public CollisionBox getCollisionBox() {
	return collisionBox;
	}
	public void setCollisionBox(CollisionBox collisionBox) {
	this.collisionBox = collisionBox;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDesc() {
		return desc;
	}
	public void setDesc(String[] desc) {
		this.desc = desc;
	}
	public BufferedImage getIcon() {
		return icon;
	}
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
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
	
}
