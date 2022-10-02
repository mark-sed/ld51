package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.sedlacek.ld51.main.Game;

public class BlackMarket extends Seller {

	public static final int UID = 2;
	private static Random r = new Random();
	
	public BlackMarket() {
		this.ID = UID;
		this.name = "Black market";
		this.plus = new String[0];
		this.minus = new String[] {
			"?"
		};
		this.plusIc = new BufferedImage[0];
		this.minusIc = new BufferedImage[] {
			Offer.iconTrading	
		};
	}
	
	@Override
	public void bought() {
		float mult = (r.nextFloat()*30+5)/1000;
		Game.player.setTradingIndex(Game.player.getTradingIndex()-mult);
	}

}
