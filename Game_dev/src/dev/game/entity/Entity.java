package dev.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;

public abstract class Entity {
	//entity coordinates
	protected float x,y;
	
	//entities width and height
	protected int width,height;
	protected Rectangle bounds;
	protected boolean active=true;
	protected int health;
	protected boolean solid=true;
	public static final int DEFAULT_HEALTH=3;
	protected Handler handler;
	//constructor
	public Entity(Handler handler,  float x, float y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.handler = handler;
		bounds = new Rectangle(0,0,width,height);
		health=DEFAULT_HEALTH;
	}
	
	public Rectangle getCollisionBounds(float xOffset,float yOffset) {
		return new Rectangle((int)(x+bounds.x+xOffset) ,(int)(y+bounds.y+yOffset),bounds.width,bounds.height);
	}
	public boolean checkEntityCollisions(float xOffset,float yOffset) {
		for(Entity e:handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset,yOffset)) && e.isSolid()) {
				return true;
				
			}
		}
		return false;
	}
	public void hurt(int damage) {
		health-=damage;
		if (health<=0) {
			active=false;
			die();
		}
	}
	//getters and setters for all variables
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void die();

}
