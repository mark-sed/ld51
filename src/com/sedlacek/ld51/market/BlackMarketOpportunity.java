package com.sedlacek.ld51.market;

import com.sedlacek.ld51.main.Game;

public class BlackMarketOpportunity  extends Opportunity {

	public static final int UID = 2;
	
	public BlackMarketOpportunity() {
		ID = UID;
		this.name = "Black market";
		this.desc = new String[] {
				"Start trading on",
				"the black market.",
				"Affects trading",
				"index."
		};
	}
	
	public static boolean canBeCreated() {
		if(Game.player.taken.contains(UID)) {
			return false;
		}
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.taken.add(this.ID);
		Game.player.traders.add(BlackMarket.UID);
	}

	@Override
	public boolean canTake() {
		return true;
	}
}
