package com.sedlacek.ld51.market;

import com.sedlacek.ld51.main.Game;

public class ExtraOpportunity extends Opportunity {

	public static final int UID = 4;
	
	public ExtraOpportunity() {
		ID = UID;
		this.name = "Opportunity";
		this.desc = new String[] {
				"- Send 20 people",
				"  on an expedition.",
				"+ Gain opportunity",
				"  card."
		};
	}
	
	public static boolean canBeCreated() {
		if(Game.player.opportBar == null) return false;
		return Game.player.opportBar.opports.size() < 4;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.takePpl(20);
		Game.player.opportBar.createCard();
	}

	@Override
	public boolean canTake() {
		return Game.player.getPpl() > 20;
	}

}
