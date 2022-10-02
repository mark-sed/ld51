package com.sedlacek.ld51.market;

import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.main.Game;

public class Taxation extends Opportunity {

	public static final int UID = 7;
	
	public Taxation() {
		ID = UID;
		this.name = "Taxation";
		this.desc = new String[] {
				"Tax your colonies.",
				"+ 5 gold for each",
				"  colony",
				"- 20 happiness in",
				"  colony"
		};
	}
	
	public static boolean canBeCreated() {
		if(Game.player.colonies.size() == 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.giveMoney(Game.player.colonies.size()*5);
		for(Planet c: Game.player.colonies) {
			c.setHappiness(c.getHappiness()-20);
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
