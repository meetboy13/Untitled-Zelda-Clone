package dev.game.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.states.Credits;
import dev.game.states.GameOverState;
import dev.game.states.State;
import dev.launcher.Assets;

public class Boss extends Creature{
	private BossHandLeft leftHand;
	private BossHandRight rightHand;
	private int delay=0;
	private float leftYDelta;
	private float rightXDelta;
	private float rightYDelta;
	private float leftXDelta;
	private int leftCooldown=30,leftAttackCount=0,leftAttackLoop=600,leftAttackLoopCount=0;
	private int meleeAttackCount=0,meleeAttackDelay=240;
	private boolean up;
	private boolean right;
	private int rightAttackCount=0;
	private int rightCooldown=120;
	private int invulnerable = 0;
	public Boss(Handler handler, float x, float y, int width, int height,BossHandLeft leftHand,BossHandRight rightHand) {
		super(handler, x, y, width, height);
		// TODO Auto-generated constructor stub
		bounds.x=(int) x;
		bounds.y=(int) y;
		bounds.width=width;
		bounds.height=height;
		this.maxHealth=20;
		this.health=20;
		this.id=7;
		this.leftHand=leftHand;
		this.rightHand=rightHand;
		this.damage=1;
	}

	@Override
	public void setStun(boolean stunned){
		if (stunned) {
			if (delay < 200) {
				delay+=20;
			}
		}
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		idleMove();
		moveHands();
		checkAttacks();
		decayInvulnerable();
		checkHandHealth();
	}

	private void checkHandHealth() {
		int trueHealth = health-20;
		if(leftHand!=null) {
			if(leftHand.getHealth()<1) {
				leftHand.die();
			}else {
				trueHealth+=leftHand.getHealth();
			}
		}
		if(rightHand!=null) {			
			if(rightHand.getHealth()<1) {
				rightHand.die();
			}else {
				trueHealth+=rightHand.getHealth();
			}
		}
		if (trueHealth<1) {
			die();
		}
	}

	private void decayInvulnerable() {
		if(invulnerable <0) {
			invulnerable--;
		}
	}

	@Override
	public void hurt(int damage,int deltaX,int deltaY) {

		if(invulnerable <0) {
			return;
		} else {
			health -= damage;
			invulnerable = 40;
		}
		if (health<1) {
			die();
		}
	}

	private void checkAttacks() {
		// TODO Auto-generated method stub
		float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();
		if(!(yDelta>-(this.getHeight()+20))) {
			
			meleeAttackCount=0;
			if(leftHand!=null) {
				leftHand.setAttacking(true);
				if(leftAttackLoopCount>=leftAttackLoop) {
					leftAttackLoopCount=0;
				}
				if (leftAttackLoopCount<=150) {
					if(leftAttackCount>leftCooldown) {
						leftAttackCount=0;
						leftHand.spreadAttack();
					}else {
						leftAttackCount++;
					}
					leftAttackLoopCount++;
				}else {
					leftAttackLoopCount++;
				}
			}
			if(rightHand!=null) {
				rightHand.setAttacking(true);
				if(rightAttackCount>rightCooldown) {
					rightAttackCount=0;
					rightHand.targetAttack();
				}else {
					rightAttackCount++;
				}
			}
		}else {
			leftHand.setAttacking(false);
			rightHand.setAttacking(false);
			if(meleeAttackCount>=meleeAttackDelay) {
				meleeAttackCount=0;
				Rectangle cb = this.getCollisionBounds(0, 0);
				Rectangle ar = null;
				ar= new Rectangle();
				int arSize=50;
				ar.height=arSize;
				ar.x=cb.x;
				ar.y=cb.y+cb.height;
				ar.width=cb.width;
				for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
					if(e.equals(this)) {continue;}
					if(e.getCollisionBounds(0, 0).intersects(ar)) {
						int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
						int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
						e.hurt(damage,deltaX,deltaY);
					}
				}
			}else {
				meleeAttackCount++;
			}
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
		if(handler.getWorld().getEntityManager().getPlayer().getX()<x) {
			g.drawImage(Assets.BossHead[2],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/meleeAttackDelay),width,height,null);
		}else if(handler.getWorld().getEntityManager().getPlayer().getX()>(x+width)){
			g.drawImage(Assets.BossHead[1],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/meleeAttackDelay),width,height,null);
		}else {
			g.drawImage(Assets.BossHead[0],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/meleeAttackDelay),width,height,null);
		}
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		active=false;
		if(leftHand!=null) {leftHand.die();}
		if(rightHand!=null) {rightHand.die();}
		Credits credits = new Credits(handler);
		State.setState(credits);
	}

}
