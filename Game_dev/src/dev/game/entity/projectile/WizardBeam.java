package dev.game.entity.projectile;

import java.awt.Graphics;
import java.util.Random;

import dev.game.Handler;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class WizardBeam extends Projectile{

	private Animation beam;
	private Random rand = new Random();
	public WizardBeam(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		// TODO Auto-generated constructor stub
		
		bounds.x+=15;
		bounds.y+=15;
		bounds.height=20;
		bounds.width=20;
		speed=2;
		int rate=rand.nextInt(40);
		beam= new Animation(rate+100, Assets.wizard_beam);
	}

	/*@Override
	public void setDirection(Direction direction) {
		if(direction==Direction.UP) {
			yMove=-speed;
		}
		else if(direction==Direction.DOWN) {
			yMove=speed;
		}
		else if(direction==Direction.LEFT) {
			xMove=-speed;
		}
		else if(direction==Direction.RIGHT) {
			xMove=speed;
		}
	}*/
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		beam.tick();
		move();
		checkForCollisions();
	}
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(beam.getCurrentFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),DEFAULT_PROJECTILE_WIDTH,DEFAULT_PROJECTILE_HEIGHT,null);
		/*
		 * code to show projectile hitboxes
		g.drawRect((int)(x+bounds.x-handler.getGameCamera().getxOffset()),(int)(y+bounds.y-handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
		*/
	}
	public void setXSpeed(float xRatio) {
		xMove = xRatio*speed;
	}
	
	public void setYSpeed(float yRatio) {
		yMove = yRatio*speed;
	}
	
	
}
