package com.sedlacek.ld51.market;

import com.sedlacek.ld51.entities.AttackFleet;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.main.Game;

public class EventBoosters extends Opportunity {

	public static final int UID = -3;
	
	public EventBoosters() {
		ID = UID;
		this.name = "Boosters";
		this.desc = new String[] {
				"All your attack fleets",
				"gain +1 speed"
		};
	}
	
	public static boolean canBeCreated() {
		return true;
	}
	
	@Override
	public void take() {
		if(!canTake()) return;
		for(Ship s: Game.player.fleet) {
			if(s instanceof AttackFleet)
				s.setSpeed(s.getSpeed()+1);
		}
	}

	@Override
	public boolean canTake() {
		return true;
	}

}
