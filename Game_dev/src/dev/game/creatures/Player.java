package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;

import dev.game.game;
import dev.game.Handler;
import dev.launcher.Assets;

public class Player extends Creature{
	
	public Player(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		getInput();
		move();
		handler.getGameCamera().centeronEntity(this);
	}

	private void getInput() {
		xMove=0;
		yMove=0;
		if(handler.getKeyManager().up) {
			yMove= -speed;
		}
		if(handler.getKeyManager().down) {
			yMove= speed;
		}
		if(handler.getKeyManager().left) {
			xMove= -speed;
		}
		if(handler.getKeyManager().right) {
			xMove= speed;
		}
	}
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.player,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
		g.setColor(Color.blue);
		g.fillRect((int)(x+bounds.x-handler.getGameCamera().getxOffset()),(int)(y+bounds.y - handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
	}
	
}
