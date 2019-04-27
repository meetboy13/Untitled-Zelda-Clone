package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.entity.projectile.WizardBeam;
import dev.launcher.Assets;

public class Statue extends StaticEntity{
	private long lastAttackTimer = 0,attackCooldown=2000,attackTimer=attackCooldown;
	
	public Statue(Handler handler,float x, float y) {
		super(handler,x,y,(int)(1.5*64/1.2), (int) (1.5*64));
		this.health=1;
		id=9;
		name="Statue";
		bounds.x=20;
		bounds.y=(int)(height/2);
		bounds.width = width-40;
		bounds.height=(int)(height-height/1.25f);
	}

	@Override
	public void hurt(int damage, int deltaX, int deltaY) {
		// TODO Auto-generated method stub	
	}
	
	public void die() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.statue,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);

		//g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);

	}
	
	private void checkAttack() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<attackCooldown) {
			return;
		}
		attackTimer = 0;
		
		float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
		float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

		float xRatio = Math.abs(xDelta)/(Math.abs(xDelta)+Math.abs(yDelta));
		float yRatio = Math.abs(yDelta)/(Math.abs(xDelta)+Math.abs(yDelta));
		
		WizardBeam attack = new WizardBeam(handler,0,0);
		attack.setX((float) (x+width/2-attack.getWidth()/2));
		attack.setY(this.getCollisionBounds(0, 0).y-attack.getHeight()/2-attack.getCollisionBounds(0, 0).height/2);

		if(xDelta>0) {
			attack.setXSpeed(-xRatio);
		}else {
			attack.setXSpeed(xRatio);
		}

		if(yDelta>0) {
			attack.setYSpeed(-yRatio);
		}else {
			attack.setYSpeed(yRatio);
		}

		handler.getWorld().getProjectileManager().addEntity(attack);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		checkAttack();
	}
}
