package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.entity.Entity;
import dev.game.entity.projectile.WizardBeam;
import dev.launcher.Assets;

public class BossHand extends Creature{
	Boss pairedBoss;
	private Rectangle cb =getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();

	public BossHand(Handler handler, float x, float y, int width, int height,Boss boss) {
		super(handler, x, y, width, height);
		pairedBoss=boss;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		System.out.println("hand Exist");
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.magic,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		g.setColor(Color.blue);
		g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
		g.drawRect((int)(ar.x-handler.getGameCamera().getxOffset()),(int)(ar.y-handler.getGameCamera().getyOffset()),ar.width,ar.height);
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
