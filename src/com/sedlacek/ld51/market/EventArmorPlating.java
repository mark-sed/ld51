package com.sedlacek.ld51.market;

import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.main.Game;

public class EventArmorPlating extends Opportunity {

	public static final int UID = -2;
	
	public EventArmorPlating() {
		ID = UID;
		this.name = "Armor plating";
		this.desc = new String[] {
				"All your ships gain",
				"50 maximum HP"
		};
	}
	
	public static boolean canBeCreated() {
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.motherShip.setMaxHp(Game.player.motherShip.getMaxHp()+50);
		Game.player.motherShip.setHp(Game.player.motherShip.getHp()+50);
		for(Ship s: Game.player.fleet) {
			s.setMaxHp(s.getMaxHp()+50);
			s.setHp(s.getHp()+50);
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
