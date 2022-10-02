package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.main.Game;

public class AmmoForFuel  extends Offer {
	
	private int amount;
	private int price;
	
	private Random r = new Random();
	
	public AmmoForFuel(Seller seller) {
		this.seller = seller;
		this.amount = r.nextInt(13)+6;
		this.price = this.amount/(r.nextInt(3)+7);
		
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
				Offer.iconAmmo
		};
		this.minusIc = new BufferedImage[] {
				Offer.iconFood
		};
	}
	
	@Override
	public void buy() {
		if(!canBuy()) return;
		Game.player.takeFuel(price);
		Game.player.giveAmmo(amount);
	}
	
	@Override
	public void highlightMissing() {
		if(!canBuy()) {
			Game.player.getStatsBar().highlightFuel(false);
		}
	}

	@Override
	public boolean canBuy() {
		return Game.player.getFuel() >= price;
	}

}
