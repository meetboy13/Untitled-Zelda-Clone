package dev.game.creatures;

import java.awt.Graphics;

import dev.game.game;
import dev.launcher.Assets;

public class Player extends Creature{
	
	public Player(game Game,float x, float y,int width, int height) {
		super(Game, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		getInput();
		move();
		Game.getGameCamera().centeronEntity(this);
	}

	private void getInput() {
		xMove=0;
		yMove=0;
		if(Game.getKeyManager().up) {
			yMove= -speed;
		}
		if(Game.getKeyManager().down) {
			yMove= speed;
		}
		if(Game.getKeyManager().left) {
			xMove= -speed;
		}
		if(Game.getKeyManager().right) {
			xMove= speed;
		}
	}
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.player,(int)(x-Game.getGameCamera().getxOffset()),(int)(y-Game.getGameCamera().getyOffset()),width,height,null);
	}
	
}
