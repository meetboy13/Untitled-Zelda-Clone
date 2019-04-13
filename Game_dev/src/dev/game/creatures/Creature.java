package dev.game.creatures;

import dev.game.entity.Entity;
import dev.game.tile.Tile;
import dev.game.Handler;

public abstract class Creature extends Entity {
	public static final float DEFAULT_SPEED=3.0f;
	public static final int DEFAULT_CREATURE_WIDTH=64;
	public static final int DEFAULT_CREATURE_HEIGHT=64;
	public static final int DEFAULT_CREATURE_DAMAGE=1;

	protected int damage;
	protected float speed;
	protected float xMove,yMove;
	protected enum Facing {UP,DOWN,LEFT,RIGHT;}
	//constructor
	public Creature(Handler handler, float x, float y,int width, int height) {
		super( handler, x, y, width, height);
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
			}
		}else if (xMove<0) {//left
			int tx= ((int)(x+xMove+bounds.x)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				x=tx*Tile.TILEWIDTH+Tile.TILEWIDTH-bounds.x;
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
			}
		}else if (yMove>0) {//down
			int ty= ((int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				y=ty*Tile.TILEHEIGHT-bounds.height-bounds.y-1;
				}
		}
	}
	
	//just calls the movement methods
	public void move() {
		if(!checkEntityCollisions(xMove,0f)) {
		moveX();
		}
		if(!checkEntityCollisions(0f,yMove)) {	
		moveY();
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
	
	
	//getters and setters for health and speed
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
