package com.sedlacek.ld51.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.InputStream;
import java.util.Random;

import javax.swing.JFrame;

import com.sedlacek.ld51.gui.EventLog;
import com.sedlacek.ld51.gui.GUIHandler;
import com.sedlacek.ld51.gui.Tutorial;
import com.sedlacek.ld51.level.Level;

/*
 * @Author: Marek Sedlacek
 * mr.mareksedlacek@gmail.com
 * Twitter: @Sedlacek
 * 
 */

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static Rectangle windowRect;
	
	private static int infoFPS;
	private static int infoTicks;
	
	public static boolean soundOn = true;
	public static boolean musicOn = true;
	
	public static Random r = new Random();
	
	public static Game game;
	public static Tutorial tutorial;
	public static boolean inTutorial;
	public static Level level;
	public static Player player;
	public static Enemy enemy;
	public static GUIHandler guiHandler;
	public static JFrame frame;
	public static Thread thread;
	
	public static enum State{
		GAME,
		GAME_OVER,
		MENU,
		PAUSE,
	}
	
	public static State state;
	
	private BufferStrategy bs;
	private Graphics g;
	private static KeyManager keyManager;
	private static MouseManager mouseManager;
	private long lastTime;

	public static boolean paused = false;

	private static boolean mute = false;
	public long deathTime;
	public boolean won = false;
	public long runtime;
	public static EventLog log;

	public Game(){
		keyManager = new KeyManager();
		this.addKeyListener(keyManager);
		mouseManager = new MouseManager();
		this.addMouseListener(mouseManager);
		this.addMouseMotionListener(mouseManager);
		this.addMouseWheelListener(mouseManager);
		
		//Sheet = ImageLoader.loadNS("/furniture.png");
		
		//FONTS
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont01.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont02.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont03.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}

		//restart();
		tutorial();
		// TODO: Remove
		state = State.GAME;
	}
	
	public void gameover(boolean won) {
		state = State.GAME_OVER;
		this.won = won;
		this.runtime = System.currentTimeMillis() - level.start;
	}
	
	private void update(){
		if(state == State.GAME){
			level.update();
			player.update();
			enemy.update();
			GUIHandler.update();
			if(inTutorial) {
				tutorial.update();
			}
		}else if(state == State.MENU){
			
		}else if(state == State.GAME_OVER){
			//GUIHandler.update();
		}
		
		if(System.currentTimeMillis() - lastTime >= 1000){
			this.lastTime = System.currentTimeMillis();
			this.fixedUpdate();
		}
		if(mouseManager.LClicked){
			mouseManager.LClicked = false;
		}
	}
	
	public void tutorial() {
		inTutorial = true;
		state = State.GAME;
		GUIHandler.objects.clear();
		player = new Player();
		player.init();
		enemy = new Enemy(player);
		level = new Level(true);
		guiHandler = new GUIHandler();
		log = new EventLog();
		enemy.base.setVision(2);
		tutorial = new Tutorial(player);
	}
	
	public void restart() {
		state = State.GAME;
		inTutorial = false;
		GUIHandler.objects.clear();
		player = new Player();
		player.init();
		enemy = new Enemy(player);
		level = new Level();
		guiHandler = new GUIHandler();
		log = new EventLog();
		log.add("Market has open!");
	}
	
	public void resize() {
		Dimension size = new Dimension(Config.WIDTH, Config.HEIGHT);
		frame.setSize(size);
		frame.setResizable(false);
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
		frame.pack();
		frame.setVisible(true);
	}

	private void fixedUpdate(){
		// Invoked every second
		if(state == State.GAME){
			// NOT USED 
		}
	}

	public static void mute(){
		if(mute){
			//AudioGalery.music.loop();
			musicOn = true;
		}else{
			musicOn = false;
		}
		mute = !mute;
	}
	
	private void input(){
		if(getKeyManager().keys[KeyManager.F1]){
			if(Config.showInfo) {
				Config.showInfo = false;
			}else {
				Config.showInfo = true;
			}
			getKeyManager().keys[KeyManager.F1] = false;
		}
		if(getKeyManager().keys[KeyManager.M] && Game.state == State.GAME){
			mute();
			getKeyManager().keys[KeyManager.M] = false;
		}
		if(state == State.GAME){
			player.input();
		}
		if(state == State.GAME_OVER) {
			if(getKeyManager().keys[KeyManager.ENTER]){
				restart();
				getKeyManager().keys[KeyManager.ENTER] = false;
			}
		}
	}
	
	private void render(){
		bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		
		g = bs.getDrawGraphics();
		//Draw
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);

		if(state == State.GAME || state == State.GAME_OVER || state == State.PAUSE){
			level.render(g);
			enemy.render(g);
			player.render(g);
			GUIHandler.render(g);
			if(inTutorial) {
				tutorial.render(g);
			}
		}
		else if(state == State.MENU){
			
		}
		
		if(state == State.GAME_OVER){
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
			g.setFont(new Font("DorFont03", Font.BOLD, 30*Config.SIZE_MULT));
			long minutes = (runtime / 1000) / 60;
			long sec = (runtime / 1000) % 60;
			if(!won) {
				g.setColor(new Color(135, 3, 3));
				g.drawString("DEFEAT!", Config.WIDTH/2-g.getFontMetrics().stringWidth("DEFEAT!")/2, Config.HEIGHT/2-6*Config.SIZE_MULT);
			}else {
				g.setColor(new Color(0, 193, 196));
				g.drawString("VICTORY!", Config.WIDTH/2-g.getFontMetrics().stringWidth("VICTORY!")/2, Config.HEIGHT/2-6*Config.SIZE_MULT);
			}
			g.setColor(new Color(180,180,180));
			g.setFont(new Font("DorFont03", Font.PLAIN, 7*Config.SIZE_MULT));
			g.drawString("Press [Enter] to restart.", Config.WIDTH/2-g.getFontMetrics().stringWidth("Press [Enter] to restart.")/2, Config.HEIGHT/2+4*Config.SIZE_MULT);
		
			g.setColor(new Color(220,220,220));
			g.setFont(new Font("DorFont03", Font.PLAIN, 9*Config.SIZE_MULT));
			g.drawString(minutes+" min "+sec+" s", Config.WIDTH/2-g.getFontMetrics().stringWidth(minutes+"min "+sec+"s")/2, 20*Config.SIZE_MULT);
		
			g.setColor(Color.white.darker().darker());
			g.setFont(new Font("DorFont03", Font.PLAIN, 6*Config.SIZE_MULT));
			g.drawString("Made for Ludum Dare 51 by Marek Sedlacek (Twitter: @Sedlacek)", Config.WIDTH/2-g.getFontMetrics().stringWidth("Made for Ludum Dare 51 by Marek Sedlacek (Twitter: @Sedlacek)")/2, Config.HEIGHT-6*Config.SIZE_MULT*4);
		}
		
		if(Config.showInfo){
			g.setFont(new Font("Consolas", Font.PLAIN, 11));
			g.setColor(Color.WHITE);
			g.drawString("FPS/Ticks: " + infoFPS + "/" + infoTicks, Config.WIDTH-100, 12);
		}
		//Dispose
		bs.show();
		g.dispose();
	}
	
	public static KeyManager getKeyManager(){
		return keyManager;
	}
	
	public static MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public static Rectangle getMouseRect(){
		return mouseManager.getMouseRect();
	}
	
	public void run(){
		this.requestFocus();
		double ns = 1000000000 / Config.fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		int frames = 0;
		
		while(Config.running){
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				input();
				update();
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(timer >= 1000000000){
				//System.out.println(ticks + "   " + frames);
				infoTicks = ticks;
				infoFPS = frames;
				ticks = 0;
				frames = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	
	
	public static void main(String [] args){
		if(args.length > 0){
			if(args[0].equals("-d") || args[0].equals("--debug")){
				Config.debugMode = true;
				Config.showInfo = true;
				Config.debug("Debug mode on");
			}
		}

		game = new Game();
		
		frame = new JFrame(Config.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Config.WIDTH=screenSize.Config.WIDTH;
		Config.HEIGHT=screenSize.Config.HEIGHT;
	    frame.setBounds(0,0,screenSize.Config.WIDTH, screenSize.Config.HEIGHT);
	    frame.setUndecorated(true);*/
	    frame.setBounds(0,0, Config.WIDTH, Config.HEIGHT);
	    windowRect = new Rectangle(0, 0, Config.WIDTH, Config.HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	    
		frame.add(game);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		game.start();
	}
	
	public void start(){
		if(Config.running)
			return;
		Config.running = true;
		thread = new Thread(this, "ld50");
		thread.start();
	}
	
	public void stop(){
		if(!Config.running)
			return;
		Config.running = false;
	}
}
