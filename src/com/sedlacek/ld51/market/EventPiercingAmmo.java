package com.sedlacek.ld51.market;

import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.main.Game;

public class EventPiercingAmmo extends Opportunity {

	public static final int UID = -1;
	
	public EventPiercingAmmo() {
		ID = UID;
		this.name = "Piercing ammo";
		this.desc = new String[] {
				"All your ships",
				"gain 2 damage"
		};
	}
	
	public static boolean canBeCreated() {
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		Game.player.motherShip.setDmg(Game.player.motherShip.getDmg()+2);
		for(Ship s: Game.player.fleet) {
			s.setDmg(s.getDmg());
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
