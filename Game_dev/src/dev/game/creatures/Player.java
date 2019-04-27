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
import dev.game.entity.projectile.StunBeam;
import dev.game.inventory.Inventory;
import dev.game.inventory.Weapons.Equipment;
import dev.game.inventory.Weapons.Sword;
import dev.game.inventory.Weapons;
import dev.game.states.GameOverState;
import dev.game.states.State;
import dev.game.tile.Tile;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;
public class Player extends Creature{

	private Animation animDown,animUp,animLeft,animRight,animDie,animDownT,animUpT,animLeftT,animRightT,
	shieldUp, shieldDown, shieldRight, shieldLeft,shieldUpT, shieldDownT, shieldRightT, shieldLeftT;

	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	private Inventory inventory;
	private boolean dead = false, shielding=false, transformed=false;//temp = false;

	protected boolean transformable = true;

	private boolean neverDamaged = true;
	private int deathLoop=0,corruption=0,corruptionMax=4000, baseDamage = 1;
	private Rectangle cb =getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private Weapons weapons;

	private int invulnerable;
	public Player(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		damage=1;
		bounds.x=14;
		bounds.y=32;
		bounds.width=36;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED;
		maxHealth=16;
		this.health=maxHealth;
		this.speed=10;
		//animations
		animDown = new Animation(150,Assets.player_down);
		animLeft = new Animation(150,Assets.player_left);
		animUp = new Animation(150,Assets.player_up);
		animRight = new Animation(150,Assets.player_right);
		shieldDown= new Animation(300,Assets.player_shield_down);
		shieldUp= new Animation(300,Assets.player_shield_up);
		shieldRight= new Animation(400,Assets.player_shield_right);
		shieldLeft= new Animation(400,Assets.player_shield_left);
		animDie = new Animation(100,Assets.player_die);
		animDownT = new Animation(150,Assets.friend_down);
		animLeftT = new Animation(150,Assets.friend_left);
		animUpT = new Animation(150,Assets.friend_up);
		animRightT = new Animation(150,Assets.friend_right);
		shieldDownT= new Animation(300,Assets.friend_shield_down);
		shieldUpT= new Animation(300,Assets.friend_shield_up);
		shieldRightT= new Animation(400,Assets.friend_shield_right);
		shieldLeftT= new Animation(400,Assets.friend_shield_left);
		inventory = new Inventory(handler);
		weapons = new Weapons(handler);
	}
	@Override
	public void move() {
		if(!checkEntityCollisions(xMove,0f)) {
			moveX();
		}else {
			if(xMove<0) {
				lastDirection=Facing.LEFT;
			}else if(xMove>0) {
				lastDirection=Facing.RIGHT;
			}
			xMove=0;
		}
		if(!checkEntityCollisions(0f,yMove)) {	
			moveY();
		}else {
			if (yMove<0) {
				lastDirection=Facing.UP;
			}else if (yMove>0) {
				lastDirection=Facing.DOWN;
			}
			yMove=0;
		}
	}

	public void expandCheck() {
		int xMove=0;
		int yMove=-16;
		if(!checkEntityCollisions(xMove,0f)) {
			if(xMove>0) {//right
				int tx= ((int)(x+xMove+bounds.x + bounds.width)/Tile.TILEWIDTH);
				if(collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
					x=tx*Tile.TILEWIDTH-bounds.x-bounds.width-1-xMove;
				}
			}else if (xMove<0) {//left
				int tx= ((int)(x+xMove+bounds.x)/Tile.TILEWIDTH);
				if(collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
					x=tx*Tile.TILEWIDTH+Tile.TILEWIDTH-bounds.x+xMove;
				}
			}
		}else {
			x-=xMove;
		}
		if(!checkEntityCollisions(0f,yMove)) {	
			if(yMove<0) {//up
				int ty= ((int)(y+yMove+bounds.y)/Tile.TILEHEIGHT);
				if(collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
					y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-bounds.y-yMove;
					yMove=0;
				}
			}else if (yMove>0) {//down
				int ty= ((int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT);
				if(collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
					y=ty*Tile.TILEHEIGHT-bounds.height-bounds.y-1+yMove;
					yMove=0;
				}
			}
		}else {
			y-=yMove;
		}
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

		shieldUp.tick();
		shieldDown.tick();
		shieldLeft.tick();
		shieldRight.tick();
		shieldUpT.tick();
		shieldDownT.tick();
		shieldLeftT.tick();
		shieldRightT.tick();

		secondaryCheck();
		stunDecay();
		flickerDecay();
		invulnerableDecay();
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
	

	private void invulnerableDecay(){
		if (invulnerable<0) {
			invulnerable--;
		}
	}

	private void secondaryCheck() {
		if (weapons.getSecondary()!=Equipment.shield) {
			shielding = false;
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
				e.hurt(damage*baseDamage,deltaX,deltaY);
			}
		}
	}

	@Override
	public void hurt(int damage,int deltaX,int deltaY) {
		if(dead) {
			return;
		}
		if(!shielding) {
			setNeverDamaged(false);
			Assets.hurt.play();
			if (transformed && transformable) {
				corruption += damage*10;
			}else {
				health-=damage;
				invulnerable = 20;
			}
			if (health<=0) {
				die();
			}
			damageFlicker=60;
		}

		//knockback
		if (transformed && !shielding) {
			return;
		}
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
	private void attack2() {
		//ranged javelin attack

		if (weapons.getSecondary()==Equipment.javelin) {
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
				attack.setX((float) (this.getCollisionBounds(0, 0).x+bounds.x*2+bounds.width-attack.getBounds().getX()));
				attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
				attack.setDirection(Direction.RIGHT);
			}else {
				return;
			}
			attackTimer=0;
			handler.getWorld().getProjectileManager().addEntity(attack);
			//weapons.setSecondary(Equipment.none);
		} else if (weapons.getSecondary()==Equipment.shield) {
			shielding = !shielding;
		} else if (weapons.getSecondary()==Equipment.wand) {
			//Make stun projectile
			StunBeam attack;
			if(lastDirection==Facing.UP) {
				attack=new StunBeam(handler,0,0);
				attack.setX((float) (x+width/2-attack.getWidth()/2));
				attack.setY(this.getCollisionBounds(0, 0).y-attack.getHeight()/2-attack.getCollisionBounds(0, 0).height/2);
				attack.setDirection(Direction.UP);
			}
			else if(lastDirection==Facing.DOWN) {
				attack=new StunBeam(handler,0,0);
				attack.setX((float) (x+width/2-attack.getWidth()/2));
				attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()));
				attack.setDirection(Direction.DOWN);
			}
			else if(lastDirection==Facing.LEFT) {
				attack=new StunBeam(handler,0, 0);
				attack.setDirection(Direction.LEFT);
				attack.setX(x-attack.getCollisionBounds(0, 0).width-10);
				attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
			}
			else if(lastDirection==Facing.RIGHT) {
				attack=new StunBeam(handler,0,0);
				attack.setX((float) (this.getCollisionBounds(0, 0).x+bounds.x*2+bounds.width-attack.getBounds().getX())-20);
				attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
				attack.setDirection(Direction.RIGHT);
			}else {
				return;
			}

			attackTimer=0;
			handler.getWorld().getProjectileManager().addEntity(attack);
		}
	}

	private void transform() {
		if(transformed) {
			speed = Creature.DEFAULT_SPEED;
			baseDamage = 1;
			bounds.x=14;
			bounds.y=32;
			bounds.width=36;
			bounds.height=32;

		}else {
			expandCheck();
			speed = Creature.DEFAULT_SPEED*0.7f;
			baseDamage = 2;
			bounds.x=14;
			bounds.y=16;
			bounds.width=36;
			bounds.height=48;
			//int tx= ((int)(x+bounds.x)/Tile.TILEWIDTH);
			//if(collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,tx) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,tx)) {
			//xMove = -3;
			//yMove = 3;
			//}
			//xMove=1;
			//yMove=-1;
		}
		//lastDirection=Facing.DOWN;
		transformed = !transformed;
	}

	private void getInput() {
		xMove=0;
		yMove=0;
		if(transformable&&handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			transform();
			//}else if(temp) {
			//xMove = 1;
			//yMove = -1;
			//}else if(temp) {
			//xMove= -1;
			//return;
		}
		else if(handler.getKeyManager().attack1) {//keyJustPressed(KeyEvent.VK_SPACE)) {
			attack1();
		}else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
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
		}if(shielding) {
			xMove=xMove*0.2f;
			yMove=yMove*0.2f;
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

		g.setColor(Color.blue);
		g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
		g.drawRect((int)(ar.x-handler.getGameCamera().getxOffset()),(int)(ar.y-handler.getGameCamera().getyOffset()),ar.width,ar.height);

	}



	private BufferedImage getCurrentAnimationFrame() {
		if(transformed) {
			if(dead) {
				return animDie.getCurrentFrame();
			}else if(shielding) {
				if((xMove==0)&&(yMove==0)) {
					if(lastDirection==Facing.UP) {
						return Assets.friend_shield_up[1];
					} else if (lastDirection==Facing.DOWN) {
						return Assets.friend_shield_down[1];
					}else if(lastDirection==Facing.LEFT) {
						return Assets.friend_shield_left[0];
					} else if (lastDirection==Facing.RIGHT) {
						return Assets.friend_shield_right[0];
					}
				}else {
					if(lastDirection==Facing.UP) {
						return shieldUpT.getCurrentFrame();
					} else if (lastDirection==Facing.DOWN) {
						return shieldDownT.getCurrentFrame();
					}else if(lastDirection==Facing.LEFT) {
						return shieldLeftT.getCurrentFrame();
					} else if (lastDirection==Facing.RIGHT) {
						return shieldRightT.getCurrentFrame();
					}
				}
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
			}else if(shielding) {
				if((xMove==0)&&(yMove==0)) {
					if(lastDirection==Facing.UP) {
						return Assets.player_shield_up[1];
					} else if (lastDirection==Facing.DOWN) {
						return Assets.player_shield_down[1];
					}else if(lastDirection==Facing.LEFT) {
						return Assets.player_shield_left[0];
					} else if (lastDirection==Facing.RIGHT) {
						return Assets.player_shield_right[0];
					}
				}else {
					if(lastDirection==Facing.UP) {
						return shieldUp.getCurrentFrame();
					} else if (lastDirection==Facing.DOWN) {
						return shieldDown.getCurrentFrame();
					}else if(lastDirection==Facing.LEFT) {
						return shieldLeft.getCurrentFrame();
					} else if (lastDirection==Facing.RIGHT) {
						return shieldRight.getCurrentFrame();
					}
				}
			}else if(xMove<0) {
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

	public void corruptionTick() {
		if (transformed &&(corruption<corruptionMax)) {
			corruption+=2;
		}/*else if((corruption>100)) {
			corruption--;
		}*/
		if(corruption == corruptionMax) {
			setTransformable(false);
			weapons.setPrimary(Weapons.Sword.OP);
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
	public boolean isNeverDamaged() {
		return neverDamaged;
	}
	public void setNeverDamaged(boolean neverDamaged) {
		this.neverDamaged = neverDamaged;
	}


}
