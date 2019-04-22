package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.entity.Entity;
import dev.game.entity.projectile.Arrow;
import dev.game.inventory.Inventory;
import dev.game.inventory.Inventory.Equipment;
import dev.game.inventory.Inventory.Sword;
import dev.game.inventory.Weapons;
import dev.game.states.GameOverState;
import dev.game.states.State;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;
public class Player extends Creature{

	private Animation animDown,animUp,animLeft,animRight,animDie,animDownT,animUpT,animLeftT,animRightT;

	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	private Inventory inventory;
	private boolean dead = false,temp=false, shielding=false, transformed=false, transformable = true;
	private int deathLoop=0,corruption=0,corruptionMax=2000, baseDamage = 0;
	private Rectangle cb =getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private Weapons weapons;
	public Player(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		damage=1;
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED;
		maxHealth=16;
		this.health=maxHealth;
		//animations
		animDown = new Animation(150,Assets.player_down);
		animLeft = new Animation(150,Assets.player_left);
		animUp = new Animation(150,Assets.player_up);
		animRight = new Animation(150,Assets.player_right);
		animDie = new Animation(100,Assets.player_die);
		animDownT = new Animation(150,Assets.friend_down);
		animLeftT = new Animation(150,Assets.friend_left);
		animUpT = new Animation(150,Assets.friend_up);
		animRightT = new Animation(150,Assets.friend_right);
		inventory = new Inventory(handler);
		weapons = new Weapons(handler);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		animDownT.tick();
		animUpT.tick();
		animRightT.tick();
		animLeftT.tick();
		stunDecay();
		flickerDecay();
		if (!dead) {
			if(!stunned) {///change later so the player can still pause
				getInput();
			}
			move();
		}else {
			animDie.tick();
		}
		handler.getGameCamera().centeronEntity(this);
		inventory.tick();
		if(transformable) {
			corruptionTick();
		}

	}

	private void attack1() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<weapons.getPrimaryCooldown()) {
			return;
		}
		// TODO Auto-generated method stub

		cb =getCollisionBounds(0,0);
		ar= new Rectangle();
		int arSize=20;
		ar.width=arSize;
		ar.height=arSize;
		ar=weapons.getHitBox(lastDirection, cb);
		attackTimer=0;
		damage=weapons.getDamagePrimary();
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
				int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
				e.hurt(damage,deltaX,deltaY);
			}
		}
	}

	private void attack2() {
		//ranged javelin attack

		if (inventory.getSecondary()==Equipment.spear) {
			Arrow attack;

			if(lastDirection==Facing.UP) {
				attack=new Arrow(handler,0,0);
				attack.setX((float) (x+width/2-attack.getWidth()/2));
				attack.setY(this.getCollisionBounds(0, 0).y-attack.getHeight()/2-attack.getCollisionBounds(0, 0).height/2);
				attack.setDirection(Direction.UP);
			}
			else if(lastDirection==Facing.DOWN) {
				attack=new Arrow(handler,0,0);
				attack.setX((float) (x+width/2-attack.getWidth()/2));
				attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()));
				attack.setDirection(Direction.DOWN);
			}
			else if(lastDirection==Facing.LEFT) {
				attack=new Arrow(handler,0, 0);
				attack.setDirection(Direction.LEFT);
				attack.setX(x-attack.getCollisionBounds(0, 0).width+10);
				attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
			}
			else if(lastDirection==Facing.RIGHT) {
				attack=new Arrow(handler,0,0);
				attack.setX((float) (this.getCollisionBounds(0, 0).x+bounds.x*2+bounds.width-this.bounds.height-attack.getBounds().getX())+10);
				attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
				attack.setDirection(Direction.RIGHT);
			}else {
				return;
			}
			attackTimer=0;
			handler.getWorld().getProjectileManager().addEntity(attack);
			inventory.setSecondary(Equipment.none);
		} else if (inventory.getSecondary()==Equipment.shield) {
			shielding = !shielding;
		}
	}

	private void getInput() {
		xMove=0;
		yMove=0;
		if(transformable&&handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			if(transformed) {
				speed = Creature.DEFAULT_SPEED;
				baseDamage = 1;
			}else {
				speed = Creature.DEFAULT_SPEED*0.7f;
				baseDamage = 2;
			}
			transformed = !transformed;
		}else if(handler.getKeyManager().attack1) {//keyJustPressed(KeyEvent.VK_SPACE)) {
			attack1();
		}else if (handler.getKeyManager().attack2) {
			attack2();
		}else if(handler.getKeyManager().up && handler.getKeyManager().right) {
			yMove= (float) (-speed/Math.sqrt(2));
			xMove= (float) (speed/Math.sqrt(2));
		}else if(handler.getKeyManager().up && handler.getKeyManager().left) {
			yMove= (float) (-speed/Math.sqrt(2));
			xMove= (float) (-speed/Math.sqrt(2));
		}else if(handler.getKeyManager().down && handler.getKeyManager().right) {
			yMove= (float) (speed/Math.sqrt(2));
			xMove= (float) (speed/Math.sqrt(2));
		}else if(handler.getKeyManager().down && handler.getKeyManager().left) {
			yMove= (float) (speed/Math.sqrt(2));
			xMove= (float) (-speed/Math.sqrt(2));
		}else if(handler.getKeyManager().up) {
			yMove= -speed;
		} else	if(handler.getKeyManager().down) {
			yMove= speed;
		}else if(handler.getKeyManager().left) {
			xMove= -speed;
		}else if(handler.getKeyManager().right) {
			xMove= speed;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (dead){
			if (deathLoop==60){
				active=false;
				State gameOverState = new GameOverState(handler);
				State.setState(gameOverState);
			}
			deathLoop++;
		}
		if (damageFlicker%20<15) {
			g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
		inventory.render(g);
		//draw hitboxes for attacks and for the player
		//this code should be generic except the ar rect which may require some temporary reworking
		/*
		g.setColor(Color.blue);
		g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
		g.drawRect((int)(ar.x-handler.getGameCamera().getxOffset()),(int)(ar.y-handler.getGameCamera().getyOffset()),ar.width,ar.height);
		 */
	}

	private BufferedImage getCurrentAnimationFrame() {
		if(transformed) {

			if(dead) {
				return animDie.getCurrentFrame();
			}
			else if(xMove<0) {
				if((yMove<0) && (lastDirection==Facing.UP)) {
					return animUpT.getCurrentFrame();
				} else if ((yMove>0) && (lastDirection==Facing.DOWN)) {
					return animDownT.getCurrentFrame();
				}
				lastDirection=Facing.LEFT;
				return animLeftT.getCurrentFrame();
			}else if(xMove>0) {
				if((yMove<0) && (lastDirection==Facing.UP)) {
					return animUpT.getCurrentFrame();
				} else if ((yMove>0) && (lastDirection==Facing.DOWN)) {
					return animDownT.getCurrentFrame();
				}
				lastDirection=Facing.RIGHT;
				return animRightT.getCurrentFrame();
			}else if (yMove<0) {
				lastDirection=Facing.UP;
				return animUpT.getCurrentFrame();
			}else if (yMove>0) {
				lastDirection=Facing.DOWN;
				return animDownT.getCurrentFrame();
			}else if (lastDirection==Facing.LEFT) {;
			return Assets.friend_left[1];
			}
			else if (lastDirection==Facing.RIGHT) {
				return Assets.friend_right[1];
			}
			else if (lastDirection==Facing.UP) {
				return Assets.friend_up[1];
			}
			else if (lastDirection==Facing.DOWN) {
				return Assets.friend_down[1];
			}
			//default animation to display if not condition is met.
			return Assets.friend_down[1];

		}else {
			if(dead) {
				return animDie.getCurrentFrame();
			}
			else if(xMove<0) {
				if((yMove<0) && (lastDirection==Facing.UP)) {
					return animUp.getCurrentFrame();
				} else if ((yMove>0) && (lastDirection==Facing.DOWN)) {
					return animDown.getCurrentFrame();
				}
				lastDirection=Facing.LEFT;
				return animLeft.getCurrentFrame();
			}else if(xMove>0) {
				if((yMove<0) && (lastDirection==Facing.UP)) {
					return animUp.getCurrentFrame();
				} else if ((yMove>0) && (lastDirection==Facing.DOWN)) {
					return animDown.getCurrentFrame();
				}
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
			return Assets.player_down[1];
		}
	}

	private void corruptionTick() {
		if (transformed &&(corruption<corruptionMax)) {
			corruption++;
		}else if((corruption>0)) {
			corruption--;
		}
		if(corruption == corruptionMax) {
			setTransformable(false);
		}

	}
	@Override
	public void die() {
		// TODO Auto-generated method stub
		dead=true;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getCorruption() {
		return corruption;
	}

	public void setCorruption(int corruption) {
		this.corruption = corruption;
	}

	public int getCorruptionMax() {
		return corruptionMax;
	}

	public void setCorruptionMax(int corruptionMax) {
		this.corruptionMax = corruptionMax;
	}

	public boolean isTransformable() {
		return transformable;
	}

	public void setTransformable(boolean transformable) {
		this.transformable = transformable;
	}

	public Weapons getWeapons() {
		return weapons;
	}

	public void setWeapons(Weapons weapons) {
		this.weapons = weapons;
	}


}
