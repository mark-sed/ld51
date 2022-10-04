package com.sedlacek.ld51.market;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public abstract class Opportunity {

	protected String name;
	protected String[] desc;
	protected int ID;
	protected String[] plus;
	protected String[] minus;
	protected BufferedImage[] plusIc, minusIc;
	
	private static Random r = new Random();
	
	public static Opportunity randomOpp() {
		ArrayList<Integer> uids = new ArrayList<Integer>();
		/* Too strong
		if(Scanner.canBeCreated()) {
			uids.add(Scanner.UID);
		}*/
		if(BlackMarketOpportunity.canBeCreated()) {
			uids.add(BlackMarketOpportunity.UID);
		}
		if(Farm.canBeCreated()) {
			uids.add(Farm.UID);
		}
		if(ExtraOpportunity.canBeCreated()) {
			uids.add(ExtraOpportunity.UID);
		}
		if(CivilianTrade.canBeCreated()) {
			uids.add(CivilianTrade.UID);
		}
		if(LevelUp.canBeCreated()) {
			uids.add(LevelUp.UID);
		}
		if(Taxation.canBeCreated()) {
			uids.add(Taxation.UID);
		}
		if(EarlyHarvest.canBeCreated()) {
			uids.add(EarlyHarvest.UID);
		}
		
		if(uids.size() > 0) {
			int uid = uids.get(r.nextInt(uids.size()));
			
			switch(uid) {
				case Scanner.UID: return new Scanner();
				case BlackMarketOpportunity.UID: return new BlackMarketOpportunity();
				case Farm.UID: return new Farm();
				case ExtraOpportunity.UID: return new ExtraOpportunity();
				case CivilianTrade.UID: return new CivilianTrade();
				case LevelUp.UID: return new LevelUp();
				case Taxation.UID: return new Taxation();
				case EarlyHarvest.UID: return new EarlyHarvest();
			}
		}
		return new NoOpportunity();
	}
	
	public static Opportunity randomEvent() {
		switch(r.nextInt(4)) {
		case 0: return new EventArmorPlating();
		case 1: return new EventBoosters();
		case 2: return new EventHealAll();
		case 3: return new EventPiercingAmmo();
		default:
			return new EventHealAll();
		}
	}

	public abstract void take();
	public abstract boolean canTake();
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getDesc() {
		return desc;
	}

	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	
}
