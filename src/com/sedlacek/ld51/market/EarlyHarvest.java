package com.sedlacek.ld51.market;

import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.main.Game;

public class EarlyHarvest extends Opportunity {

	public static final int UID = 8;
	
	public EarlyHarvest() {
		ID = UID;
		this.name = "Early harvest";
		this.desc = new String[] {
				"Force an early harvest.",
				"+ 5 food for each",
				"  colony",
				"- 15 happiness in",
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
		Game.player.giveFood(Game.player.colonies.size()*5);
		for(Planet c: Game.player.colonies) {
			c.setHappiness(c.getHappiness()-15);
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
