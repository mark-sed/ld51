package com.sedlacek.ld51.market;

import com.sedlacek.ld51.main.Game;

public class CivilianTrade extends Opportunity {

	public static final int UID = 5;
	
	public CivilianTrade() {
		ID = UID;
		this.name = "Civilian trade";
		this.desc = new String[] {
				"Start trading with",
				"civilians.",
				"= Exchange market.",
				"= Lower trade amounts."
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
		Game.player.traders.add(Civilians.UID);
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
