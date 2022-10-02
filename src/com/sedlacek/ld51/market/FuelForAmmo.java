package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.main.Game;

public class FuelForAmmo extends Offer {
	
	private int amount;
	private int price;
	
	private Random r = new Random();
	
	public FuelForAmmo(Seller seller) {
		this.seller = seller;
		this.amount = r.nextInt(8)+2;
		this.price = this.amount*(r.nextInt(4)+6);
		
		if(this.price <= 0) {
			this.price = 1;
		}
	
		this.plus = new String[] {
				""+this.amount
		};
		this.minus = new String[] {
				""+this.price
		};
		this.plusIc = new BufferedImage[] {
				Offer.iconFuel
		};
		this.minusIc = new BufferedImage[] {
				Offer.iconAmmo
		};
	}
	
	@Override
	public void buy() {
		if(!canBuy()) return;
		Game.player.takeAmmo(price);
		Game.player.giveFuel(amount);
	}
	
	@Override
	public void highlightMissing() {
		if(!canBuy()) {
			Game.player.getStatsBar().highlightAmmo(false);
		}
	}

	@Override
	public boolean canBuy() {
		return Game.player.getAmmo() >= price;
	}

}
