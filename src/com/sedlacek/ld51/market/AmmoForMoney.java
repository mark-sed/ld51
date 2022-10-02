package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.main.Game;

public class AmmoForMoney extends Offer {
	
	private int amount;
	private int price;
	
	private Random r = new Random();
	
	public AmmoForMoney(Seller seller) {
		this.seller = seller;
		this.amount = r.nextInt(30)+10;
		this.price = this.amount/4;
		if(seller.getID() != BlackMarket.UID) {
			if(this.amount > 9 && Game.player.getTradingIndex() > -0.5f) {
				this.price -= r.nextInt(5)+1;
			}
			if(Game.player.getTradingIndex() < -0.6) {
				this.price += r.nextInt(4)+2*(1+Game.player.getTradingIndex());
			}
			if(Game.player.getTradingIndex() < -1f) {
				this.price += 50*(Math.abs(Game.player.getTradingIndex()));
			}
		} else {
			this.price -= r.nextInt(5)+3;
		}
		
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
				Offer.iconMoney
		};
	}
	
	@Override
	public void buy() {
		if(!canBuy()) return;
		Game.player.takeMoney(price);
		Game.player.giveAmmo(amount);
	}
	
	@Override
	public void highlightMissing() {
		if(!canBuy()) {
			Game.player.getStatsBar().highlightMoney(false);
		}
	}

	@Override
	public boolean canBuy() {
		return Game.player.getMoney() >= price;
	}

}
