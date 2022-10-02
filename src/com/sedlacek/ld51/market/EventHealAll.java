package com.sedlacek.ld51.market;

import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.main.Game;

public class EventHealAll extends Opportunity {

	public static final int UID = -4;
	
	public EventHealAll() {
		ID = UID;
		this.name = "Repair shop";
		this.desc = new String[] {
				"Restore all your ships",
				"to full health"
		};
	}
	
	public static boolean canBeCreated() {
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.motherShip.setHp(Game.player.motherShip.getMaxHp());
		for(Ship s: Game.player.fleet) {
			s.setHp(s.getMaxHp());
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
