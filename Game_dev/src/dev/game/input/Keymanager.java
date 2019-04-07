package dev.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keymanager implements KeyListener{
	private boolean[] keys;
	public boolean up,down,left,right;
	
	public Keymanager() {
		keys = new boolean[256];
	}
	public void tick() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
	}
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		keys[arg0.getKeyCode()]=true;
		System.out.println("Pressed");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

		keys[arg0.getKeyCode()]=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
