package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;

public abstract class Seller {

	protected String name;
	protected String[] plus;
	protected String[] minus;
	protected BufferedImage[] plusIc, minusIc;
	protected int ID;
	
	public void bought() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getPlus() {
		return plus;
	}
	public void setPlus(String[] plus) {
		this.plus = plus;
	}
	public String[] getMinus() {
		return minus;
	}
	public void setMinus(String[] minus) {
		this.minus = minus;
	}
	public BufferedImage[] getPlusIc() {
		return plusIc;
	}
	public void setPlusIc(BufferedImage[] plusIc) {
		this.plusIc = plusIc;
	}
	public BufferedImage[] getMinusIc() {
		return minusIc;
	}
	public void setMinusIc(BufferedImage[] minusIc) {
		this.minusIc = minusIc;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
}
