package com.sedlacek.ld51.level;

import java.awt.Graphics;

import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.gui.Timer;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public abstract class Planet extends Entity {

	protected int foodGen, fuelGen, pplGen, moneyGen, ammoGen;
	protected float TIGen;
	protected Timer timer;
	protected int happiness;
	protected int maxHappiness;
	private long lastTime;
	
	public Planet(int col, int row) {
		this.col = col;
		this.row = row;
		this.status = Status.NEUTRAL;
		this.timer = new Timer();
		this.maxHappiness = 200;
		this.happiness = 100;
	}
	
	public void generateResources() {
		// TODO: Add chance for riots
		int extraMoney = happiness > 100 ? (happiness - 100) / 20 : 0;
		if(foodGen > 0)
			Game.player.setFood(Game.player.getFood()+foodGen);
		if(fuelGen > 0)
			Game.player.setFuel(Game.player.getFuel()+fuelGen);
		if(ammoGen > 0)
			Game.player.setAmmo(Game.player.getAmmo()+ammoGen);
		if(pplGen > 0)
			Game.player.setPpl(Game.player.getPpl()+pplGen);
		if(TIGen > 0 && Game.player.getTradingIndex() < 2f)
			Game.player.setTradingIndex(Game.player.getTradingIndex()+TIGen);
		if(moneyGen+extraMoney != 0)
			Game.player.setMoney(Game.player.getMoney()+moneyGen+extraMoney);
		if(isPlayerNear()) {
			if(this.happiness <= this.maxHappiness) {
				this.happiness += 1;
			}
		}
		
	}
	
	public boolean isPlayerNear() {
		for(int i = getCol()-1; i <= getCol()+1; ++i) {
			for(int j = getRow()-1; j <= getRow()+1; ++j) {
				if(i < 0 || i >= Level.W || j < 0 || j >= Level.H) continue;
				Entity oc = Game.level.map[i][j].getOccupier();
				if(oc instanceof Ship && ((Ship)oc).getStatus() == Status.FRIENDLY) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void update() {
		if(status == Status.FRIENDLY && isPlayerNear()) {
			this.timer.update();
			if(this.timer.finished) {
				generateResources();
				this.timer = new Timer();
			}
		}
		else if(status == Status.FRIENDLY && !isPlayerNear() && System.currentTimeMillis() - lastTime >= 20000) {
			Game.log.add(name+" has no fleet near to export goods.");
			Game.log.add("To collect resources send a ship.");
			lastTime = System.currentTimeMillis();
		}
	}
	
	public void render(Graphics g, int x, int y, Tile parent) {
		super.render(g, x, y, parent);
		if(status == Status.FRIENDLY && isPlayerNear()) {
			timer.render(g, x, y+Config.CURR_T_SIZE - 2*Config.SIZE_MULT, Config.CURR_T_SIZE, 1*Config.SIZE_MULT, false);
		}
	}
	
	@Override
	public void render(Graphics g) {
		
	}

	public int getFoodGen() {
		return foodGen;
	}

	public void setFoodGen(int foodGen) {
		this.foodGen = foodGen;
	}

	public int getFuelGen() {
		return fuelGen;
	}

	public void setFuelGen(int fuelGen) {
		this.fuelGen = fuelGen;
	}

	public int getPplGen() {
		return pplGen;
	}

	public void setPplGen(int pplGen) {
		this.pplGen = pplGen;
	}

	public int getMoneyGen() {
		return moneyGen;
	}

	public void setMoneyGen(int moneyGen) {
		this.moneyGen = moneyGen;
	}

	public int getAmmoGen() {
		return ammoGen;
	}

	public void setAmmoGen(int ammoGen) {
		this.ammoGen = ammoGen;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public int getMaxHappiness() {
		return maxHappiness;
	}

	public void setMaxHappiness(int maxHappiness) {
		this.maxHappiness = maxHappiness;
	}
	
	
}
