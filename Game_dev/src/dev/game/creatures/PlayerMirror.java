package dev.game.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.entity.projectile.Arrow;
import dev.game.entity.projectile.StunBeam;
import dev.game.inventory.Inventory;
import dev.game.inventory.Weapons.Equipment;
import dev.game.inventory.Weapons.Sword;
import dev.game.sound.Sounds;
import dev.game.inventory.Weapons;
import dev.game.states.GameOverState;
import dev.game.states.State;
import dev.game.tile.Tile;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;
public class PlayerMirror extends Creature{

	private Animation animDown,animUp,animLeft,animRight,animDie,animDownT,animUpT,animLeftT,animRightT,
	shieldUp, shieldDown, shieldRight, shieldLeft,shieldUpT, shieldDownT, shieldRightT, shieldLeftT;

	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	private long lastSecondaryTimer,secondaryCooldown=2000,secondaryTimer=secondaryCooldown;
	private Inventory inventory;
	private boolean dead = false, shielding=false, transformed=false, transformable = true;
	private int deathLoop=0,corruption=0,corruptionMax=2000, baseDamage = 1, attacking = 0;
	private Rectangle cb =getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private Weapons weapons;
	private Player playerPair;
	private int invulnerable = 0;
	public PlayerMirror(Handler handler,float x, float y,int width, int height,Player playerPair) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		damage=1;
		bounds.x=14;
		bounds.width=32;

		//set initial appearance and bounds
		if(playerPair.getTransformed()) {
			speed = Creature.DEFAULT_SPEED*0.7f;
			baseDamage = 2;
			bounds.y=16;
			bounds.height=48;
			transformed = false;

		}else {

			transformed = true;
			speed = Creature.DEFAULT_SPEED;
			baseDamage = 1;
			bounds.y=32;
			bounds.height=32;
		}
		shielding = playerPair.getShielding();
		this.id= 9999999;
		maxHealth=playerPair.getMaxHealth();
		this.playerPair=playerPair;
		this.health=playerPair.getMaxHealth();
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
		inventory = playerPair.getInventory();
		weapons = playerPair.getWeapons();
	}

	//tick animations
	public void animationTick() {
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
	}

	@Override
	public void tick() {
		animationTick();
		stunDecay();
		flickerDecay();
		getpairMove();
		copyPlayer();
		invulnerableDecay();
		if (!dead) {
			move();
			getInput();
		}else {
			animDie.tick();
		}

	}

	private void invulnerableDecay(){
		if (invulnerable>0) {
			invulnerable--;
		}
	}

	//mimic player stance and facing direction
	private void copyPlayer(){
		shielding = playerPair.getShielding();
		lastDirection = playerPair.getFacing();
	}

	//get player movement
	private void getpairMove() {
		xMove=playerPair.getxMove();
		yMove=playerPair.getyMove();
	}

	//move with the player
	@Override
	public void move() {
		if(!checkEntityCollisions(xMove,0f)) {
			moveX();
		}else {
			playerPair.setX(playerPair.getX()-playerPair.getxMove());
		}
		if(!checkEntityCollisions(0f,yMove)) {	
			moveY();
		}else {
			playerPair.setY(playerPair.getY()-playerPair.getyMove());
		}
	}

	//move in the X direction
	@Override	
	public void moveX() {
		if(xMove>0) {//right
			int tx= ((int)(x+xMove+bounds.x + bounds.width)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				x=tx*Tile.TILEWIDTH-bounds.x-bounds.width-1;
				playerPair.setX(playerPair.getX()-playerPair.getxMove());
			}
		}else if (xMove<0) {//left
			int tx= ((int)(x+xMove+bounds.x)/Tile.TILEWIDTH);
			if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx,(int)(y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x+=xMove;
			}else {
				x=tx*Tile.TILEWIDTH+Tile.TILEWIDTH-bounds.x;
				playerPair.setX(playerPair.getX()-playerPair.getxMove());
			}
		}
	}

	//move in the Y direction
	@Override
	public void moveY() {
		if(yMove<0) {//up
			int ty= ((int)(y+yMove+bounds.y)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				y=ty*Tile.TILEHEIGHT+Tile.TILEHEIGHT-bounds.y;
				playerPair.setY(playerPair.getY()-playerPair.getyMove());
			}
		}else if (yMove>0) {//down
			int ty= ((int)(y+yMove+bounds.y+bounds.height)/Tile.TILEHEIGHT);
			if(!collisionWithTile((int)(x+bounds.x)/Tile.TILEWIDTH,ty) && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILEWIDTH,ty)) {
				y+=yMove;
			}else {
				y=ty*Tile.TILEHEIGHT-bounds.height-bounds.y-1;
				playerPair.setY(playerPair.getY()-playerPair.getyMove());
			}
		}
	}

	//sword attack
	private void attack1() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<weapons.getPrimaryCooldown()) {
			return;
		}
		attacking = 5;
		cb =getCollisionBounds(0,0);
		ar= new Rectangle();
		int arSize;
		if(transformed) {
			arSize=25;
		}else {
			arSize=20;
		}
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

	//Don't set stun
	@Override 
	public void setStun(boolean stunned) {
		return;
	}

	//set damage to player
	@Override
	public void hurt(int damage,int deltaX,int deltaY) {
		if(dead) {
			return;
		}
		if(invulnerable<0) {
			return;
		}
		if(!shielding) {
			playerPair.setNeverDamaged(false);
			Sounds.hurt.play();
			if (transformed && transformable) {
				corruption += damage*50;
			}else {
				health-=damage;
				invulnerable = 40;
			}
			if (health<=0) {
				die();
			}
			if (damageFlicker<21) {
				damageFlicker=60;
			}
		}
	}

	//secondary attack
	private void attack2() {
		secondaryTimer+=System.currentTimeMillis()-lastSecondaryTimer;
		lastSecondaryTimer=System.currentTimeMillis();
		if(secondaryTimer<weapons.getSecondaryCooldown()) {
			return;
		}

		secondaryTimer=0;

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
			handler.getWorld().getProjectileManager().addEntity(attack);
		} else if (weapons.getSecondary()==Equipment.shield) {
			//change shield stance
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
			handler.getWorld().getProjectileManager().addEntity(attack);
		}
	}

	//get attack input()
	private void getInput() {
		xMove=0;
		yMove=0;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
			attack1();
		}else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
			attack2();
		}
		xMove=playerPair.getxMove();
		yMove=playerPair.getyMove();
	}

	@Override
	public void render(Graphics g) {
		//start death animation and change to game over screen
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
	}


	//get animation frame for rendering
	private BufferedImage getCurrentAnimationFrame() {
		if(transformed) {
			if(dead) {
				return animDie.getCurrentFrame();
			}else if(attacking>0) {
				attacking--;
				if (weapons.getPrimary()==Sword.mirror) {
					if(lastDirection==Facing.UP) {
						return Assets.friend_mirror_up[2];
					} else if (lastDirection==Facing.DOWN) {
						return Assets.friend_mirror_down[2];
					}else if(lastDirection==Facing.LEFT) {
						return Assets.friend_mirror_left[2];
					} else if (lastDirection==Facing.RIGHT) {
						return Assets.friend_mirror_right[2];
					}
				} else if(weapons.getPrimary()==Sword.OP) {
					if(lastDirection==Facing.UP) {
						return Assets.friend_op_up[2];
					} else if (lastDirection==Facing.DOWN) {
						return Assets.friend_op_down[2];
					}else if(lastDirection==Facing.LEFT) {
						return Assets.friend_op_left[2];
					} else if (lastDirection==Facing.RIGHT) {
						return Assets.friend_op_right[2];
					}
				}
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
			}else if(attacking>0) {
				attacking--;
				if (weapons.getPrimary()==Sword.mirror) {
					if(lastDirection==Facing.UP) {
						return Assets.player_mirror_up[2];
					} else if (lastDirection==Facing.DOWN) {
						return Assets.player_mirror_down[2];
					}else if(lastDirection==Facing.LEFT) {
						return Assets.player_mirror_left[2];
					} else if (lastDirection==Facing.RIGHT) {
						return Assets.player_mirror_right[2];
					}
				} else if(weapons.getPrimary()==Sword.OP) {
					if(lastDirection==Facing.UP) {
						return Assets.player_op_up[2];
					} else if (lastDirection==Facing.DOWN) {
						return Assets.player_op_down[2];
					}else if(lastDirection==Facing.LEFT) {
						return Assets.player_op_left[2];
					} else if (lastDirection==Facing.RIGHT) {
						return Assets.player_op_right[2];
					}
				}
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

	//start death animation
	@Override
	public void die() {
		dead=true;
	}

	//getters and setters
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
