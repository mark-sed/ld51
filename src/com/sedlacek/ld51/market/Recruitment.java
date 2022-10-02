package com.sedlacek.ld51.market;

import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.main.Game;

public class Recruitment  extends Opportunity {

	public static final int UID = 8;
	
	public Recruitment() {
		ID = UID;
		this.name = "Recruitment";
		this.desc = new String[] {
				"Recruitment.",
				"+ 4 people for each",
				"  colony",
				"- 10 happiness in",
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
		Game.player.givePpl(Game.player.colonies.size()*4);
		for(Planet c: Game.player.colonies) {
			c.setHappiness(c.getHappiness()-10);
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}


}
