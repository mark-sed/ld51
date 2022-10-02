package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;

public class TradeFederation extends Seller {

	public static final int UID = 1;
	
	public TradeFederation() {
		this.ID = UID;
		this.name = "Trade union";
		this.plus = new String[0];
		this.minus = new String[0];
		this.plusIc = new BufferedImage[0];
		this.minusIc = new BufferedImage[0];
	}
}
