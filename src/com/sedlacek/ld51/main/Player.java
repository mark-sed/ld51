package com.sedlacek.ld51.main;

import java.awt.Graphics;
import java.util.ArrayList;

import com.sedlacek.ld51.entities.AttackFleet;
import com.sedlacek.ld51.entities.Entity;
import com.sedlacek.ld51.entities.MotherShip;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.gui.GUIHandler;
import com.sedlacek.ld51.gui.Highlighter;
import com.sedlacek.ld51.gui.InfoBox;
import com.sedlacek.ld51.gui.Market;
import com.sedlacek.ld51.gui.OpportunityBar;
import com.sedlacek.ld51.gui.ShipMove;
import com.sedlacek.ld51.gui.StatsBar;
import com.sedlacek.ld51.level.Level;
import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.market.TradeFederation;

public class Player {
	
	public MotherShip motherShip;
	public StatsBar statsBar;
	public Market market;
	public InfoBox infoBox;
	public OpportunityBar opportBar;
	public ArrayList<Integer> taken;
	
	public Entity selected;
	
	private int money, food, fuel, ammo, ppl;
	private float tradingIndex;
	public float speedMult;
	public ArrayList<Integer> traders;
	public ArrayList<Planet> colonies;
	public ArrayList<Ship> fleet;
	
	public int colonizationsDone;
	
	public int passiveIncome;
	public int extraIncome;
	
	public float foodMult;
	public int farming;
	
	public int level;
	public float repairDelayMult;
	
	public Player() {
		this.taken = new ArrayList<Integer>();
		this.traders = new ArrayList<Integer>();
		this.colonies = new ArrayList<Planet>();
		this.fleet = new ArrayList<Ship>();
		this.traders.add(TradeFederation.UID);
		
		this.money = 15;
		this.food = 20;
		this.fuel = 8;
		this.ammo = 0;
		this.ppl = 10;
		this.tradingIndex = 0.0f;
		
		this.speedMult = 1.0f;
		this.repairDelayMult = 1.0f;
		
		this.passiveIncome = 5;
		this.extraIncome = 0;
		
		this.foodMult = 1.0f;
		this.farming = 0;
		
		this.level = 1;
		
		colonizationsDone = 0;
	}
	
	
	public void init() {
		this.infoBox = new InfoBox();
		GUIHandler.objects.add(this.infoBox);

		this.statsBar = new StatsBar(this);
		GUIHandler.objects.add(this.statsBar);
		
		this.market = new Market(this);
		GUIHandler.objects.add(this.market);
		
		this.opportBar = new OpportunityBar(this);
		GUIHandler.objects.add(this.opportBar);
	}
	
	public void update() {
		for(int i = 0; i < fleet.size(); ++i) {
			if(fleet.get(i).getStacks() <= 0) {
				Game.log.add(fleet.get(i).getName()+" has been destroyed.");
				if(Game.player.infoBox.shipSelected && ((Ship)Game.player.infoBox.go) == fleet.get(i)) {
					Game.player.infoBox.unselect();
				}
				Game.level.map[fleet.get(i).getCol()][fleet.get(i).getRow()].setOccupier(null);
				fleet.remove(i);
				if(Game.soundOn)
		    		AudioGalery.explosion.playClip();
			}
		}
		if(motherShip.getStacks() <= 0) {
			Game.game.gameover(false);
		}
	}
	
	public void input() {
		if(Game.getKeyManager().keys[KeyManager.ESC]){
			infoBox.setObject(null);
			Tile.selected = null;
			Game.getKeyManager().keys[KeyManager.ESC] = false;
		}
	}
	
	public void render(Graphics g) {
		
	}
	
	public void move(GameObject s, int col, int row) {
		if(s instanceof Ship) {
			int moves = Math.abs(s.getCol()-col)+Math.abs(s.getRow()-row);
			this.takeFuel(moves);
			
			Ship ship = (Ship)s;
			new ShipMove(ship, col, row);
		}
		
	}
	
	public boolean canMove(GameObject s, int col, int row) {
		int moves = Math.abs(s.getCol()-col)+Math.abs(s.getRow()-row);
		return moves <= fuel && Game.level.map[col][row].getOccupier() == null;
	}
	
	public boolean canSee(Entity s, int col, int row) {
		int moves = Math.abs(s.getCol()-col)+Math.abs(s.getRow()-row);
		return moves <= s.getVision();
	}

	public void addShip(Ship s) {
		if(s instanceof AttackFleet) {
			for(Ship s2: Game.player.fleet) {
				if(s2 instanceof AttackFleet && Math.abs(s2.getCol()-motherShip.getCol()) <= 1 && Math.abs(s2.getRow()-motherShip.getRow()) <= 1) {
					s2.setStacks(s2.getStacks()+s.getStacks());
					new Highlighter(Level.col2X(s2.getCol()), Level.row2Y(s2.getRow()), Config.CURR_T_SIZE, Config.CURR_T_SIZE, true);
					return;
				}
			}
		}
		
		this.fleet.add(s);
		new Highlighter(Level.col2X(s.getCol()), Level.row2Y(s.getRow()), Config.CURR_T_SIZE, Config.CURR_T_SIZE, true);
		Game.level.map[s.getCol()][s.getRow()].setOccupier(s);
	}
	
	public MotherShip getMotherShip() {
		return motherShip;
	}

	public void setMotherShip(MotherShip motherShip) {
		this.motherShip = motherShip;
	}

	public StatsBar getStatsBar() {
		return statsBar;
	}

	public void setStatsBar(StatsBar statsBar) {
		this.statsBar = statsBar;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		statsBar.highlightMoney(money > this.money);
		this.money = money;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		statsBar.highlightFood(food > this.food);
		this.food = food;
	}

	public float getTradingIndex() {
		return tradingIndex;
	}


	public void setTradingIndex(float tradingIndex) {
		statsBar.highlightTI(tradingIndex > this.tradingIndex);
		this.tradingIndex = tradingIndex;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		statsBar.highlightFuel(fuel > this.fuel);
		this.fuel = fuel;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		statsBar.highlightAmmo(ammo > this.ammo);
		this.ammo = ammo;
	}
	
	public int getPpl() {
		return ppl;
	}

	public void setPpl(int ppl) {
		statsBar.highlightPpl(ppl > this.ppl);
		this.ppl = ppl;
	}
	
	public void takePpl(int am) {
		this.setPpl(this.getPpl()-am);
	}
	
	public void givePpl(int am) {
		this.setPpl(this.getPpl()+am);
	}

	public void takeMoney(int am) {
		this.setMoney(this.getMoney()-am);
	}
	
	public void giveMoney(int am) {
		this.setMoney(this.getMoney()+am);
	}
	
	public void takeFuel(int am) {
		this.setFuel(this.getFuel()-am);
	}
	
	public void giveFuel(int am) {
		this.setFuel(this.getFuel()+am);
	}
	
	public void takeFood(int am) {
		this.setFood(this.getFood()-am);
	}
	
	public void giveFood(int am) {
		this.setFood(this.getFood()+am);
	}
	
	public void takeAmmo(int am) {
		this.setAmmo(this.getAmmo()-am);
	}
	
	public void giveAmmo(int am) {
		this.setAmmo(this.getAmmo()+am);
	}


	
}
