package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.inventory.Inventory;
import dev.game.states.GameOverState;
import dev.game.states.GameState;
import dev.game.states.State;
import dev.launcher.Animation;
import dev.launcher.Assets;
public class Player extends Creature{
	
	private Animation animDown,animUp,animLeft,animRight;
	
	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	
	private enum Facing {UP,DOWN,LEFT,RIGHT;}
	private Facing lastDirection=Facing.DOWN;
	private Inventory inventory;
	public Player(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED;
		//animations
		animDown = new Animation(150,Assets.player_down);
		animLeft = new Animation(150,Assets.player_left);
		animUp = new Animation(150,Assets.player_up);
		animRight = new Animation(150,Assets.player_right);
		inventory = new Inventory(handler);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		getInput();
		move();
		handler.getGameCamera().centeronEntity(this);
		checkAttacks();
		inventory.tick();
	}
	private void checkAttacks() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<attackCooldown) {
			return;
		}
		// TODO Auto-generated method stub
		Rectangle cb =getCollisionBounds(0,0);
		Rectangle ar= new Rectangle();
		int arSize=20;
		ar.width=arSize;
		ar.height=arSize;
		if(handler.getKeyManager().attack){
			if(lastDirection==Facing.UP) {
				ar.x=cb.x+cb.width/2-arSize/2;
				ar.y=cb.y-arSize;
			}
			else if(lastDirection==Facing.DOWN) {
				ar.x=cb.x+cb.width/2-arSize/2;
				ar.y=cb.y+cb.height;
			}
			else if(lastDirection==Facing.LEFT) {
				ar.x=cb.x-arSize;
				ar.y=cb.y+cb.height/2-arSize/2;
			}
			else if(lastDirection==Facing.RIGHT) {
				ar.x=cb.x+cb.width;
				ar.y=cb.y+cb.height/2-arSize/2;
			}else {
				return;
			}
			attackTimer=0;
			for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
				if(e.equals(this)) {continue;}
				if(e.getCollisionBounds(0, 0).intersects(ar)) {
					e.hurt(1);
				}
			}
		}
	}

	private void getInput() {
		xMove=0;
		yMove=0;
		if(handler.getKeyManager().up) {
			yMove= -speed;
		}
		if(handler.getKeyManager().down) {
			yMove= speed;
		}
		if(handler.getKeyManager().left) {
			xMove= -speed;
		}
		if(handler.getKeyManager().right) {
			xMove= speed;
		}
	}
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		inventory.render(g);
		/* bounding box code, commented out in case needed for debugging later
		g.setColor(Color.blue);
		g.fillRect((int)(x+bounds.x-handler.getGameCamera().getxOffset()),(int)(y+bounds.y - handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
		*/
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove<0) {
			lastDirection=Facing.LEFT;
			return animLeft.getCurrentFrame();
		}else if(xMove>0) {
			lastDirection=Facing.RIGHT;
			return animRight.getCurrentFrame();
		}else if (yMove<0) {
			lastDirection=Facing.UP;
			return animUp.getCurrentFrame();
		}else if (yMove>0) {
			lastDirection=Facing.DOWN;
			return animDown.getCurrentFrame();
		}else if (lastDirection==Facing.LEFT) {;
			return Assets.player_left[1];
		}
		else if (lastDirection==Facing.RIGHT) {
			return Assets.player_right[1];
		}
		else if (lastDirection==Facing.UP) {
			return Assets.player_up[1];
		}
		else if (lastDirection==Facing.DOWN) {
			return Assets.player_down[1];
		}
		//default animation to display if not condition is met.
		return Assets.player;
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		System.out.println("You died");
		State gameOverState = new GameOverState(handler);
		State.setState(gameOverState);
		
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
}
