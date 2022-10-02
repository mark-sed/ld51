package com.sedlacek.ld51.market;

import com.sedlacek.ld51.main.Game;

public class Farm extends Opportunity {

	public static final int UID = 3;
	
	public Farm() {
		ID = UID;
		this.name = "Farming outpost";
		this.desc = new String[] {
				"- Send 5 people on a",
				"  farming outpost",
				"+ Generate +1 food."
		};
	}
	
	public static boolean canBeCreated() {
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.takePpl(5);
		Game.player.farming += 1;
	}

	@Override
	public boolean canTake() {
		return Game.player.getPpl() >= 5;
	}

}
