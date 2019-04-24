package dev.game.creatures;

import java.awt.Graphics;

import dev.game.Handler;
import dev.launcher.Assets;

public class Boss extends Creature{
	private BossHandLeft leftHand;
	private BossHandRight rightHand;
	private int delay=0;
	private float leftYDelta;
	private float rightXDelta;
	private float rightYDelta;
	private float leftXDelta;
	private int leftCooldown=40,leftAttackCount=0;
	private boolean up;
	private boolean right;
	private int rightAttackCount=0;
	private int rightCooldown=120;
	public Boss(Handler handler, float x, float y, int width, int height,BossHandLeft leftHand,BossHandRight rightHand) {
		super(handler, x, y, width, height);
		// TODO Auto-generated constructor stub
		this.maxHealth=20;
		this.health=20;
		this.id=7;
		this.leftHand=leftHand;
		this.rightHand=rightHand;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		idleMove();
		moveHands();
		checkAttacks();
	}

	private void checkAttacks() {
		// TODO Auto-generated method stub
		if(leftAttackCount>leftCooldown) {
			leftAttackCount=0;
			leftHand.spreadAttack();
		}else {
			leftAttackCount++;
		}
		if(rightAttackCount>rightCooldown) {
			rightAttackCount=0;
			rightHand.targetAttack();
		}else {
			rightAttackCount++;
		}
	}

	private void idleMove() {
		// TODO Auto-generated method stub
		if (leftYDelta>10||leftYDelta<-10) {
			up=!up;
		}
		if(up) {
			leftYDelta+=0.25;
		}else {
			leftYDelta-=0.25;
		}
		if (leftXDelta>15||leftXDelta<-15) {
			right=!right;
		}
		if(right) {
			leftXDelta+=0.25;
		}else {
			leftXDelta-=0.25;
		}
		rightXDelta=leftXDelta;
		rightYDelta=leftYDelta;
	}

	private void moveHands() {
		// TODO Auto-generated method stub
		leftHand.setX(this.getX()+this.getWidth()/2-leftHand.getWidth()/2-100+leftXDelta);
		leftHand.setY(this.getY()+this.getHeight()/2-leftHand.getHeight()/2+100+leftYDelta);
		rightHand.setX(this.getX()+this.getWidth()/2-rightHand.getWidth()/2+100+rightXDelta);
		rightHand.setY(this.getY()+this.getHeight()/2-rightHand.getHeight()/2+100+rightYDelta);
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
