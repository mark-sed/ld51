package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;

public class Civilians extends Seller {

	public static final int UID = 3;
	
	public Civilians() {
		this.ID = UID;
		this.name = "Civilians";
		this.plus = new String[0];
		this.minus = new String[0];
		this.plusIc = new BufferedImage[0];
		this.minusIc = new BufferedImage[0];
	}

}
