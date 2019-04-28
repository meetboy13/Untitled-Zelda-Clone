package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.entity.Entity;
import dev.game.entity.projectile.WizardBeam;
import dev.launcher.Assets;

public class BossHandLeft extends Creature{
	Boss pairedBoss;
	private Rectangle cb =getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private int invulnerable=0;
	private boolean attacking = false;

	public BossHandLeft(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		id=9999999;
		this.solid=false;
		this.health = 20;
		this.health = 20;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		damageOnTouch();
		decayInvulnerable();
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
			invulnerable = 30;
		}
		if (health < 0){
			health = 0;
		}
	}
	
	private void damageOnTouch() {
		Rectangle cb =getCollisionBounds(0,0);
		Rectangle ar= new Rectangle();
		ar.width=bounds.width-20;
		ar.height=+bounds.height-20;
		ar.y=cb.y+10;
		ar.x=cb.x+10;
			if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(ar)) {
				int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).x+handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).width/2));
				int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).y+handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).height/2));
				handler.getWorld().getEntityManager().getPlayer().hurt(1,deltaX,deltaY);
		}
	}
	
	public void spreadAttack() {
		float xDelta = 0;
		float yDelta = -80;
		float xRatio = Math.abs(xDelta)/(Math.abs(xDelta)+Math.abs(yDelta));
		float yRatio = Math.abs(yDelta)/(Math.abs(xDelta)+Math.abs(yDelta));

		WizardBeam attack;
		attack=new WizardBeam(handler,0,0);
		attack.setX((float) (x+width/2-attack.getWidth()/2));
		attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()+20));
		attack.setXSpeed(xRatio);
		attack.setYSpeed(yRatio);
		handler.getWorld().getProjectileManager().addEntity(attack);
		xDelta = 10;
		while (xDelta<=600) {
			xRatio = Math.abs(xDelta)/(Math.abs(xDelta)+Math.abs(yDelta));
			yRatio = Math.abs(yDelta)/(Math.abs(xDelta)+Math.abs(yDelta));

	
			attack=new WizardBeam(handler,0,0);
			attack.setX((float) (x+width/2-attack.getWidth()/2)+40-xDelta/60);
			attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()));
			attack.setXSpeed(xRatio);
			attack.setYSpeed(yRatio);
			handler.getWorld().getProjectileManager().addEntity(attack);
			attack=new WizardBeam(handler,0,0);
			attack.setX((float) (x+width/2-attack.getWidth()/2)-40+xDelta/60);
			attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()));
			attack.setYSpeed(yRatio);
			attack.setXSpeed(-xRatio);
			handler.getWorld().getProjectileManager().addEntity(attack);
			xDelta=(float) (xDelta*1.5+50);
		}
	}
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (attacking) {
			g.drawImage(Assets.BossHandRight[2],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}else {
			g.drawImage(Assets.BossHandRight[0],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
		g.setColor(Color.blue);
		//g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
		g.drawRect((int)(ar.x-handler.getGameCamera().getxOffset()),(int)(ar.y-handler.getGameCamera().getyOffset()),ar.width,ar.height);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		active = false;
		handler.getWorld().getEntityManager().getPlayer().setScore(handler.getWorld().getEntityManager().getPlayer().getScore()+500);
		
	}
	
	@Override
	public void setStun(boolean stunned){
		
	}
	
	public boolean isAttacking() {
		return attacking;
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

}
