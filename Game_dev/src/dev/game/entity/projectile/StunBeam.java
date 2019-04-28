package dev.game.entity.projectile;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class StunBeam extends Projectile{
	private Animation beam;

	public StunBeam(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDirection(Direction direction) {
		if(direction==Direction.UP) {
			yMove=-speed;
			beam= new Animation(100, Assets.stunbeamUp);
			this.getBounds().y=10;
			this.getBounds().x=10;
			this.getBounds().width=DEFAULT_PROJECTILE_WIDTH-20;
			this.getBounds().height=DEFAULT_PROJECTILE_HEIGHT-20;
		}
		else if(direction==Direction.DOWN) {
			yMove=speed;
			beam= new Animation(100, Assets.stunbeamDown);
			this.getBounds().y=15;
			this.getBounds().x=10;
			this.getBounds().width=DEFAULT_PROJECTILE_WIDTH-20;
			this.getBounds().height=DEFAULT_PROJECTILE_HEIGHT-20;
		}
		else if(direction==Direction.LEFT) {
			xMove=-speed;
			beam= new Animation(100, Assets.stunbeamLeft);
			this.getBounds().y=10;
			this.getBounds().x=10;
			this.getBounds().width=DEFAULT_PROJECTILE_WIDTH-20;
			this.getBounds().height=DEFAULT_PROJECTILE_HEIGHT-20;
		}
		else if(direction==Direction.RIGHT) {
			xMove=speed;
			beam= new Animation(100, Assets.stunbeamRight);
			this.getBounds().y=10;
			this.getBounds().x=10;
			this.getBounds().width=DEFAULT_PROJECTILE_WIDTH-20;
			this.getBounds().height=DEFAULT_PROJECTILE_HEIGHT-20;
		}
	}
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(beam.getCurrentFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),DEFAULT_PROJECTILE_WIDTH,DEFAULT_PROJECTILE_HEIGHT,null);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		beam.tick();
		move();
		checkForCollisions();
	}
	
	@Override
	public void checkForCollisions() {
		boolean hit=false;
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))&& e.isSolid()) {
				//int deltaX=(int) (this.getX()-e.getX());
				//int deltaY=(int) (this.getY()-e.getY());
				e.setStun(true);
				hit=true;
			}
		}
		if (hit) {
			this.die();
		}
	}
	
}
