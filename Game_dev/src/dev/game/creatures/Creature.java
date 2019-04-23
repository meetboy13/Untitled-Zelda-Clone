package dev.game.creatures;

import dev.game.entity.Entity;
import dev.game.tile.Tile;
import dev.game.Handler;
import dev.game.creatures.Creature.Facing;

public abstract class Creature extends Entity {
	public static final float DEFAULT_SPEED=3.0f;
	public static final int DEFAULT_CREATURE_WIDTH=64;
	public static final int DEFAULT_CREATURE_HEIGHT=64;
	public static final int DEFAULT_CREATURE_DAMAGE=1;
	public static final int DEFAULT_CREATURE_MAX_HEALTH=10;

	protected int damage,maxHealth;
	protected float speed;
	protected float xMove,yMove;
	public enum Facing {UP,DOWN,LEFT,RIGHT;}
	protected Facing lastDirection=Facing.DOWN;
	protected boolean stunned = false;
	protected int stunnedDuration=0,damageFlicker=0;
	//constructor
	public Creature(Handler handler, float x, float y,int width, int height) {
		super( handler, x, y, width, height);
		maxHealth=DEFAULT_CREATURE_MAX_HEALTH;
		damage=DEFAULT_CREATURE_DAMAGE;
		speed=DEFAULT_SPEED;
		xMove =0;
		yMove = 0;
	}

	//check for collisions, if none then move
	public void moveX() {
		if(xMove>0) {//right
			int tx= ((int)(x+xMove+bounds.x + bounds.width)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				x=tx*Tile.TILEWIDTH-bounds.x-bounds.width-1;
				xMove=0;
			}
		}else if (xMove<0) {//left
			int tx= ((int)(x+xMove+bounds.x)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				x=tx*Tile.TILEWIDTH+Tile.TILEWIDTH-bounds.x;
				xMove=0;
			}
		}
	}
	public void moveY() {
		if(yMove<0) {//up
			int ty= ((int)(y+yMove+bounds.y)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-bounds.y;
				yMove=0;
			}
		}else if (yMove>0) {//down
			int ty= ((int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				y=ty*Tile.TILEHEIGHT-bounds.height-bounds.y-1;
				yMove=0;
				}
		}
	}
	@Override
	public void hurt(int damage,int deltaX,int deltaY) {
		
		health-=damage;
		if (health<=0) {
			die();
		}
		//knockback
		damageFlicker=60;
		if (deltaX<0) {
			xMove=(speed*4);
		}else {
			xMove=-(4*speed);
		}
		if(deltaY<0) {
			yMove=(speed*4);
		}else {
			yMove=-(4*speed);
		}
		stunned=true;
		stunnedDuration=2;
	}
	//just calls the movement methods
	public void move() {
		if(!checkEntityCollisions(xMove,0f)) {
			moveX();
		}else {
			xMove=0;
		}
		if(!checkEntityCollisions(0f,yMove)) {	
			moveY();
		}else {
			yMove=0;
		}
	}
	protected void stunDecay() {
		if (stunnedDuration>0) {
			stunnedDuration--;
		}else if(stunned) {
			stunned=false;
		}
	}
	public void flickerDecay() {
		if (damageFlicker>0) {
			damageFlicker--;
		}
	}
	//just checks if tile at coords is solid or not
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	//getters and setters
	public float getxMove() {
		return xMove;
	}
	public void setxMove(float xMove) {
		this.xMove = xMove;
	}
	public float getyMove() {
		return yMove;
	}
	public void setyMove(float ymove) {
		yMove = ymove;
	}
	public void setStun(boolean stunned){
		this.stunned = stunned;
	}
	
	
	//getters and setters for health and speed
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if (this.health>maxHealth){
			this.health=maxHealth;
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
}
