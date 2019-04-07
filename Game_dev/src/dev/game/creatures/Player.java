package dev.game.creatures;

import java.awt.Graphics;

import dev.game.game;
import dev.launcher.Assets;

public class Player extends Creature{
	private game Game;
	public Player(game Game,float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.Game = Game;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(Game.getKeyManager().up) {
			y-=3;
		}
		if(Game.getKeyManager().down) {
			y+=3;
		}
		if(Game.getKeyManager().left) {
			x-=3;
		}
		if(Game.getKeyManager().right) {
			x+=3;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.player,(int)x,(int)y,null);
	}
	
}
