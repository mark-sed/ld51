package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.main.Game;

public class PplForMoney extends Offer {
	
	private int amount;
	private int price;
	
	private Random r = new Random();
	
	public PplForMoney(Seller seller) {
		this.seller = seller;
		this.amount = r.nextInt(2)+1;
		this.price = (r.nextInt(2)+4)*this.amount;
	
		this.plus = new String[] {
				""+this.amount
		};
		this.minus = new String[] {
				""+this.price,
				"Likness"
		};
		this.plusIc = new BufferedImage[] {
				Offer.iconPpl
		};
		this.minusIc = new BufferedImage[] {
				Offer.iconMoney
		};
	}
	
	@Override
	public void buy() {
		if(!canBuy()) return;
		Game.player.takeMoney(price);
		Game.player.givePpl(amount);
		if(Game.player.colonies.size() > 0) {
			Planet p = Game.player.colonies.get(r.nextInt(Game.player.colonies.size()));
			p.setHappiness(p.getHappiness() - (r.nextInt(4)+1));
		}
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
