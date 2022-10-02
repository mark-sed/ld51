package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.sedlacek.ld51.entities.AttackFleet;
import com.sedlacek.ld51.entities.MotherShip;
import com.sedlacek.ld51.entities.Ship;
import com.sedlacek.ld51.level.Planet;
import com.sedlacek.ld51.level.Tile;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;
import com.sedlacek.ld51.main.GameObject;
import com.sedlacek.ld51.main.KeyManager;
import com.sedlacek.ld51.main.Player;

public class Tutorial {

	private int state = 0;
	private ArrayList<TutorialText> tips;
	private Player p;
	
	public Tutorial(Player p) {
		p.statsBar.hide = true;
		p.opportBar.hide = true;
		p.infoBox.hide = true;
		p.market.hide = true;
		Game.log.hide = true;
		
		p.setFood(25);
		p.motherShip.setVision(3);
		Game.enemy.base.setVision(0);
		Game.enemy.base.setHp(50);
		this.p = p;
		
		tips = new ArrayList<TutorialText>();
		
	}
	
	public void update() {
		if(Game.getKeyManager().keys[KeyManager.F2]){
			Game.game.restart();
			Game.getKeyManager().keys[KeyManager.F2] = false;
		}
		
		if(state == 0) {
			tips.add(new TutorialText(p.motherShip, 
					p.motherShip.getX()+Config.CURR_T_SIZE, 
					p.motherShip.getY(), 
					"This is your mother ship!",
					"Protect it at all cost. (Press <Enter> or <click> to skip this).",
					10000));
			++state;
		}
		if(state == 1 && tips.size() == 0) {
			p.infoBox.hide = false;
			tips.add(new TutorialText(p.motherShip, 
					102*Config.SIZE_MULT, 
					Config.HEIGHT-60*Config.SIZE_MULT, 
					"Information box",
					"This shows info about selected object.",
					10000));
			++state;
		}
		if(state == 2 && tips.size() == 0) {
			tips.add(new TutorialText(p.motherShip, 
					p.motherShip.getX()+Config.CURR_T_SIZE, 
					p.motherShip.getY(), 
					"Click on your mother ship",
					"Clicking on a ship selects it.",
					10000));
			++state;
		}
		if(state == 3) {
			tips.clear();
			if(p.infoBox.shipSelected && p.infoBox.go instanceof MotherShip) {
				tips.add(new TutorialText(p.motherShip, 
						p.motherShip.getX()+Config.CURR_T_SIZE, 
						p.motherShip.getY(), 
						"You can now click on void square to move there",
						"Move on the 3rd column. This will uncover nebula fog.",
						10000));
				++state;
			}
		}
		if(state == 4 && p.motherShip.getCol() >= 2) {
			Game.level.uncoverFog = true;
			tips.clear();
			tips.add(new TutorialText(p.motherShip, 
					Game.enemy.base.getX()+Config.CURR_T_SIZE, 
					Game.enemy.base.getY(), 
					"This is enemy base",
					"You need to destroy it to win.",
					10000));
			++state;
		}
		if(state == 5 && tips.size() == 0) {
			tips.clear();
			p.statsBar.hide = false;
			tips.add(new TutorialText(p.motherShip, 
					0, 
					11*Config.SIZE_MULT, 
					"Money",
					"For trading",
					60000));
			
			tips.add(new TutorialText(p.motherShip, 
					32*Config.SIZE_MULT, 
					11*Config.SIZE_MULT, 
					"Food",
					"For people",
					60000));
			
			tips.add(new TutorialText(p.motherShip,  
					64*Config.SIZE_MULT, 
					11*Config.SIZE_MULT, 
					"Fuel",
					"For ships",
					60000));
			
			tips.add(new TutorialText(p.motherShip, 
					92*Config.SIZE_MULT, 
					11*Config.SIZE_MULT, 
					"Ammo",
					"To fight",
					60000));
			tips.add(new TutorialText(p.motherShip, 
					120*Config.SIZE_MULT, 
					11*Config.SIZE_MULT, 
					"People",
					"Work force",
					60000));
			tips.add(new TutorialText(p.motherShip, 
					Config.WIDTH-40*Config.SIZE_MULT, 
					11*Config.SIZE_MULT, 
					"Trading index",
					"Effect prices.",
					60000));
			++state;
		}
		if(state == 6 && tips.size() == 0) {
			tips.clear();
			p.market.hide = false;
			p.setFood(0);
			p.setMoney(100);
			tips.add(new TutorialText(p.motherShip, 
					Config.WIDTH-90*Config.SIZE_MULT, 
					16*Config.SIZE_MULT, 
					"Buy some food.",
					"Food is needed for your population.",
					10000));
			++state;
		}
		if(state == 7 && p.getFood() > 0) {
			tips.clear();
			p.opportBar.hide = false;
			tips.add(new TutorialText(p.motherShip, 
					Config.WIDTH/2-40*Config.SIZE_MULT, 
					Config.HEIGHT-80*Config.SIZE_MULT, 
					"There are also opportunities",
					"Those have special effects.",
					10000));
			++state;
		}
		if(state == 8 && tips.size() == 0) {
			tips.clear();
			tips.add(new TutorialText(p.motherShip, 
					p.motherShip.getX()+Config.CURR_T_SIZE, 
					p.motherShip.getY(), 
					"Blue voids are for building.",
					"Click on one building tile.",
					10000));
			++state;
		}
		if(state == 9 && p.infoBox.go != null) {
			tips.clear();
			p.setPpl(50);
			GameObject e = p.infoBox.go;
			if(e instanceof Tile && ((Tile)e).isBuildable()) {
				tips.add(new TutorialText(p.motherShip, 
						102*Config.SIZE_MULT, 
						Config.HEIGHT-60*Config.SIZE_MULT,
						"Build an attack fleet.",
						"Select it in the info box",
						10000));
				++state;
			}
		}
		if(state == 10 && p.fleet.size() > 0) {
			tips.clear();
			tips.add(new TutorialText(p.motherShip, 
					102*Config.SIZE_MULT, 
					Config.HEIGHT-60*Config.SIZE_MULT,
					"Build 2 more!",
					"You need fleets to colonize.",
					10000));
			++state;
		}
		if(state == 11) {
			tips.clear();
			int stacks = 0;
			for(Ship c : p.fleet) {
				if(c instanceof AttackFleet)
					stacks+=c.getStacks();
			}
			if(p.fleet.size() > 2 || stacks > 2) {
				p.setFuel(20);
				tips.add(new TutorialText(p.motherShip, 
						p.fleet.get(0).getX()+Config.CURR_T_SIZE, 
						p.fleet.get(0).getY(),
						"Move your fleet next to a planet.",
						"And click on the planet.",
						10000));
				++state;
			}
		}
		if(state == 12) {
			tips.clear();
			GameObject e = p.infoBox.go;
			if(e != null && e instanceof Planet && p.infoBox.canColonize()) {
				tips.add(new TutorialText(p.motherShip, 
						102*Config.SIZE_MULT, 
						Config.HEIGHT-60*Config.SIZE_MULT,
						"Colonize!",
						"Click on the colonize button.",
						10000));
				++state;
			}
		}
		if(state == 13 && tips.size() == 0) {
			tips.clear();
			tips.add(new TutorialText(p.motherShip, 
					102*Config.SIZE_MULT, 
					Config.HEIGHT-60*Config.SIZE_MULT,
					"Collect resources.",
					"Ship needs to be next to a planet to collect resources.",
					10000));
			++state;
		}
		if(state == 14 && tips.size() == 0) {
			tips.clear();
			p.setAmmo(0);
			p.setMoney(150);
			tips.add(new TutorialText(p.motherShip, 
					Config.WIDTH/2-30*Config.SIZE_MULT, 
					Config.HEIGHT/2,
					"No ammo!",
					"Buy some!",
					10000));
			++state;
		}
		if(state == 15 && p.getAmmo() > 0) {
			tips.clear();
			p.setPpl(50);
			p.giveAmmo(50);
			Game.log.hide = false;
			tips.add(new TutorialText(p.motherShip, 
					Config.WIDTH/2-60*Config.SIZE_MULT, 
					Config.HEIGHT/2-10*Config.SIZE_MULT,
					"Destroy enemy base!",
					"(Build units to destroy it).",
					10000));
			++state;
			tips.add(new TutorialText(p.motherShip, 
					Config.WIDTH/2-70*Config.SIZE_MULT, 
					Config.HEIGHT/2+15*Config.SIZE_MULT,
					"Good luck!",
					"(Tutorial is over once enemy is gone)",
					10000));
			++state;
		}
		
		for(int i = 0; i < tips.size(); ++i) {
			tips.get(i).update();
			if(tips.get(i).isDone()) {
				tips.remove(i);
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < tips.size(); ++i) {
			tips.get(i).render(g);
		}
		
		g.setFont(new Font("DorFont02", Font.BOLD, 7*Config.SIZE_MULT));
		g.setColor(Color.WHITE);
		g.drawString("[F2] to skip tutorial", Config.WIDTH-45*Config.SIZE_MULT, Config.HEIGHT-8*Config.SIZE_MULT);
	}
	
}
