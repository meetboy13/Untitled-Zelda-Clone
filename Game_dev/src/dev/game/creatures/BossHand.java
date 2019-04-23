package dev.game.creatures;

import java.awt.Graphics;

import dev.game.Handler;
import dev.launcher.Assets;

public class BossHand extends Creature{
	Boss pairedBoss;
	public BossHand(Handler handler, float x, float y, int width, int height,Boss boss) {
		super(handler, x, y, width, height);
		pairedBoss=boss;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.magic,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
