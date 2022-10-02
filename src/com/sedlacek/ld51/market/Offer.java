package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.graphics.ImageLoader;
import com.sedlacek.ld51.graphics.SpriteSheet;
import com.sedlacek.ld51.main.Game;

public abstract class Offer {

	protected String[] plus;
	protected String[] minus;
	protected BufferedImage[] plusIc, minusIc;
	protected Seller seller;
	
	public static SpriteSheet iconSet = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
	public static BufferedImage iconMoney = iconSet.grabImage(1, 1, 8, 8, 8);
	public static BufferedImage iconFood = iconSet.grabImage(2, 1, 8, 8, 8);
	public static BufferedImage iconFuel = iconSet.grabImage(3, 1, 8, 8, 8);
	public static BufferedImage iconAmmo = iconSet.grabImage(4, 1, 8, 8, 8);
	public static BufferedImage iconPpl = iconSet.grabImage(5, 1, 8, 8, 8);
	public static BufferedImage iconTrading = iconSet.grabImage(6, 1, 8, 8, 8);
	public static BufferedImage iconNebula = iconSet.grabImage(1, 2, 8, 8, 8);
	public static BufferedImage iconFog = iconSet.grabImage(2, 2, 8, 8, 8);
	
	private static Random r = new Random();
	
	public static Offer randomOffer() {
		int rs = Game.player.traders.get(r.nextInt(Game.player.traders.size()));
		Seller s;
		if(rs == TradeFederation.UID) {
			s = new TradeFederation();
		}
		else if(rs == BlackMarket.UID){
			s = new BlackMarket();
		}
		else if(rs == Civilians.UID) {
			s = new Civilians();
			switch(r.nextInt(6)) {
				case 0: return new FuelForAmmo(s);
				case 1: return new FuelForFood(s);
				case 2: return new AmmoForFuel(s);
				case 3: return new AmmoForFood(s);
				case 4: return new FoodForFuel(s);
				case 5: return new FoodForAmmo(s);
				default: return new FuelForAmmo(s);
			}

		}
		else {
			s = new TradeFederation();
		}
		
		switch(r.nextInt(3+(rs == BlackMarket.UID ? 1 : 0))) {
			case 0: return new FuelForMoney(s);
			case 1: return new AmmoForMoney(s);
			case 2: return new FoodForMoney(s);
			case 3: return new PplForMoney(s);
			default: return new FuelForMoney(s);
		}
	}
	
	public abstract void highlightMissing();
	
	public abstract void buy();
	public abstract boolean canBuy();

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

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public static SpriteSheet getIconSet() {
		return iconSet;
	}

	public static void setIconSet(SpriteSheet iconSet) {
		Offer.iconSet = iconSet;
	}

	public static BufferedImage getIconMoney() {
		return iconMoney;
	}

	public static void setIconMoney(BufferedImage iconMoney) {
		Offer.iconMoney = iconMoney;
	}

	public static BufferedImage getIconFood() {
		return iconFood;
	}

	public static void setIconFood(BufferedImage iconFood) {
		Offer.iconFood = iconFood;
	}

	public static BufferedImage getIconFuel() {
		return iconFuel;
	}

	public static void setIconFuel(BufferedImage iconFuel) {
		Offer.iconFuel = iconFuel;
	}

	public static BufferedImage getIconAmmo() {
		return iconAmmo;
	}

	public static void setIconAmmo(BufferedImage iconAmmo) {
		Offer.iconAmmo = iconAmmo;
	}
}
