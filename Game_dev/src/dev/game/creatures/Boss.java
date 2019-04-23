package dev.game.creatures;

import java.awt.Graphics;

import dev.game.Handler;
import dev.launcher.Assets;

public class Boss extends Creature{
	private BossHand leftHand,rightHand;
	private int delay=0;
	public Boss(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		// TODO Auto-generated constructor stub
		this.maxHealth=20;
		this.health=20;
		this.id=7;
		leftHand=new BossHand(handler,300,400, 50, 50,this);
		rightHand=new BossHand(handler,400,400, 50, 50,this);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (delay>600){
			System.out.println("Ticking");
		}else if(delay==600) {
		}
		delay++;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.magic,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		leftHand.render(g);
		rightHand.render(g);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
