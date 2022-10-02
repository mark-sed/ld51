package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.main.Game;

public class FoodForFuel extends Offer {
	
	private int amount;
	private int price;
	
	private Random r = new Random();
	
	public FoodForFuel(Seller seller) {
		this.seller = seller;
		this.amount = r.nextInt(12)+5;
		this.price = this.amount/(r.nextInt(3)+3);
		
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
				Offer.iconFood
		};
		this.minusIc = new BufferedImage[] {
				Offer.iconFuel
		};
	}
	
	@Override
	public void buy() {
		if(!canBuy()) return;
		Game.player.takeFuel(price);
		Game.player.giveFood(amount);
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
