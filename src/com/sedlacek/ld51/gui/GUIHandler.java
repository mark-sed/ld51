package com.sedlacek.ld51.gui;

import java.awt.Graphics;
import java.util.ArrayList;

public class GUIHandler {

	public static ArrayList<GUIObject> objects = new ArrayList<GUIObject>();
	
	public GUIHandler() {
	}
	
	public static void update() {
		for(int i = 0; i < objects.size(); i++) {
			GUIObject go = objects.get(i);
			go.update();
			if(go.isDone() && !go.hide) {
				objects.remove(go);
				i--;
			}
		}
	}
	
	public static void render(Graphics g) {
		for(GUIObject go: objects) {
			if(!go.hide && !go.isDone())
				go.render(g);
		}
	}
}
