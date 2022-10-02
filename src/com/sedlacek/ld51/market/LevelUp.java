package com.sedlacek.ld51.market;

import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.level.PlanetAnik;
import com.sedlacek.ld51.main.Game;

public class LevelUp extends Opportunity {

	public static final int UID = 6;
	private static boolean brokeBarricade = false;
	
	public LevelUp() {
		ID = UID;
		this.name = "Level up";
		this.desc = new String[] {
				"Level your mother",
				"ship."
		};
	}
	
	public static boolean canBeCreated() {
		if((Game.player.level < 2 && Game.player.colonizationsDone > 0) || (brokeBarricade && Game.player.level < 3)) {
			return true;
		}
		if(Game.player.level == 2 && !brokeBarricade) {
			for(Planet p: Game.player.colonies) {
				// Broke barricade
				if(p instanceof PlanetAnik) {
					brokeBarricade = true;
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.level+=1;
		if(Game.player.level == 2) {
			Game.player.motherShip.setDmg(3);
			Game.player.motherShip.setMaxHp(500);
			Game.player.motherShip.setHp(500);
		}
		else if(Game.player.level == 3) {
			Game.player.motherShip.setDmg(5);
			Game.player.motherShip.setMaxHp(700);
			Game.player.motherShip.setHp(700);
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
