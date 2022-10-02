package com.sedlacek.ld51.market;

import com.sedlacek.ld51.main.Game;

public class Scanner extends Opportunity {

	public static final int UID = 1;
	
	public Scanner() {
		ID = UID;
		this.name = "Scanner";
		this.desc = new String[] {
				"+ You can see through",
				"  Nebula.",
				"- Your speed is halved."
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
		Game.player.speedMult /= 2f;
		Game.level.uncoverFog = true;
	}

	@Override
	public boolean canTake() {
		return true;
	}
}
