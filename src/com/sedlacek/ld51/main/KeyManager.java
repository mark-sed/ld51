package com.sedlacek.ld51.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	public static final int S = KeyEvent.VK_S;
	public static final int W = KeyEvent.VK_W;
	public static final int A = KeyEvent.VK_A;
	public static final int UP = KeyEvent.VK_UP;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int SPACE = KeyEvent.VK_SPACE;
	public static final int R = KeyEvent.VK_R;
	public static final int T = KeyEvent.VK_T;
	public static final int ENTER=KeyEvent.VK_ENTER;
	public static final int F1=KeyEvent.VK_F1;
	public static final int ESC=KeyEvent.VK_ESCAPE;
	public static final int M=KeyEvent.VK_M;
	public static final int D=KeyEvent.VK_D;
	public static final int C=KeyEvent.VK_C;
	public static final int N=KeyEvent.VK_N;
	public static final int H=KeyEvent.VK_H;
	public static final int F2=KeyEvent.VK_F2;
	
	public static boolean rightSide = false,
							rightSideCtrl = false;
	
	public boolean[] keys = new boolean[525];
	public static boolean anyKeyPressed = false;
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		anyKeyPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
				rightSide = true;
			else
				rightSide = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_CONTROL){
			if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
				rightSideCtrl = true;
			else
				rightSideCtrl = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		anyKeyPressed = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
