package dev.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	private boolean[] keys,justPressed,cantPress;
	public boolean up,down,left,right,aUp,aDown,aLeft,aRight;
	
	public KeyManager() {
		keys = new boolean[256];
		justPressed= new boolean[keys.length];
		cantPress= new boolean[keys.length];
	}
	public void tick() {
		for(int i=0;i<keys.length;i++) {
			if(cantPress[i] && !keys[i]) {
				cantPress[i]=false;
			}else if (justPressed[i]) {
				cantPress[i]=true;
				justPressed[i]=false;
			}
			if(!cantPress[i]&&keys[i]) {
				justPressed[i]=true;
			}
		}
		
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		aUp = keys[KeyEvent.VK_UP];
		aDown = keys[KeyEvent.VK_DOWN];
		aLeft = keys[KeyEvent.VK_LEFT];
		aRight = keys[KeyEvent.VK_RIGHT];
	}
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()<0||arg0.getKeyCode()>=keys.length) {return;}
		keys[arg0.getKeyCode()]=true;
		System.out.println("Pressed");
	}
	public boolean keyJustPressed(int keyCode){
				if(keyCode < 0 || keyCode >= keys.length){
					return false;}
				return justPressed[keyCode];
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
