package dev.game.entity.projectile;

import java.awt.Graphics;
import java.util.Random;

import dev.game.Handler;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class WizardBeam extends Projectile{

	private Animation beam;
	private Random rand = new Random();
	public WizardBeam(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		// TODO Auto-generated constructor stub
		/*
		bounds.x+=23;
		bounds.y+=5;
		bounds.height-=10;
		bounds.width-=23*2;
		*/
		int rate=rand.nextInt(20);
		beam= new Animation(rate+20, Assets.wizard_beam);
	}

	@Override
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
	}
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
	}

}
