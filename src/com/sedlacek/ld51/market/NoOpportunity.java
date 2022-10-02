package com.sedlacek.ld51.market;

public class NoOpportunity extends Opportunity {

	public static final int UID = 0;
	
	public NoOpportunity() {
		ID = UID;
		this.name = "No opportunities";
		this.desc = new String[] {
				"There are no",
				"opportunities.",
		};
	}
	
	public static boolean canBeCreated() {
		return true;
	}
	
	@Override
	public void take() {
	}

	@Override
	public boolean canTake() {
		return false;
	}

}
