package dev.game.entity.projectile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.tile.Tile;
import dev.game.worlds.World.Direction;
import dev.launcher.Assets;

public abstract class Projectile extends Entity{
	public static final float DEFAULT_PROJECTILE_SPEED=5.0f;
	public static final int DEFAULT_PROJECTILE_WIDTH=50;
	public static final int DEFAULT_PROJECTILE_HEIGHT=50;
	public static final int DEFAULT_PROJECTILE_DAMAGE=1;
	protected int damage;
	protected float xMove,yMove,speed;
	protected BufferedImage texture;
	public Projectile(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		speed=DEFAULT_PROJECTILE_SPEED;
		damage=DEFAULT_PROJECTILE_DAMAGE;
		this.solid=false;
	}

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
	
	public void moveX() {
		if(xMove>0) {//right
			int tx= ((int)(x+xMove+bounds.x + bounds.width)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				die();
			}
		}else if (xMove<0) {//left
			int tx= ((int)(x+xMove+bounds.x)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				die();
			}
		}
	}
	public void moveY() {
		if(yMove<0) {//up
			int ty= ((int)(y+yMove+bounds.y)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				die();
			}
		}else if (yMove>0) {//down
			int ty= ((int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				die();
				}
		}
	}
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	
	public void checkForCollisions() {
		boolean damaged=false;
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))&& e.isSolid()) {
				e.hurt(damage);
				damaged=true;
			}
		}
		if (damaged) {
			this.die();
		}
	}
	public void move() {
		moveX();
		moveY();
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		move();
		checkForCollisions();
	}
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(texture,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),DEFAULT_PROJECTILE_WIDTH,DEFAULT_PROJECTILE_HEIGHT,null);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		this.active=false;
	}

}
