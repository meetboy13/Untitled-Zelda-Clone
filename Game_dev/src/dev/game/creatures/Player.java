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
import dev.game.states.HighScore;
import dev.game.states.State;
import dev.game.tile.Tile;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;
public class Player extends Creature{

	private Animation animDown,animUp,animLeft,animRight,animDie,animDownT,animUpT,animLeftT,animRightT,
	shieldUp, shieldDown, shieldRight, shieldLeft,shieldUpT, shieldDownT, shieldRightT, shieldLeftT;

	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	private long lastSecondaryTimer,secondaryCooldown=2000,secondaryTimer=secondaryCooldown;
	private Inventory inventory;
	private boolean dead = false, shielding=false, transformed=false;
	private int attacking = 0;

	protected boolean transformable = true;

	private boolean neverDamaged = true;
	private int deathLoop=0,corruption=0,corruptionMax=4000, baseDamage = 5;
	private Rectangle cb =getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private Weapons weapons;
	private int invulnerable,score=0;;

	//create player
	public Player(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		damage=1;
		bounds.x=14;
		bounds.y=32;
		bounds.width=36;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED;
		maxHealth=16;
		this.health=maxHealth;
		this.speed=3;
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
		weapons = new Weapons();
	}

	//move the player
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

	//check if transforming causes clipping and prevent it
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
		secondaryCheck();
		stunDecay();
		flickerDecay();
		invulnerableDecay();
		if (!dead) {
			if(!stunned) {
				getInput();
			}
			move();
		}else {
			animDie.tick();
		}
		if(transformable) {
			corruptionTick();
		}
		handler.getGameCamera().centeronEntity(this);
		inventory.tick();
	}

	private void invulnerableDecay(){
		if (invulnerable>0) {
			invulnerable--;
		}
	}

	//prevent shielding when changing equipment
	private void secondaryCheck() {
		if (weapons.getSecondary()!=Equipment.shield) {
			shielding = false;
		}
	}

	//sword attack in front of player
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

	//Don't set stun for player
	@Override 
	public void setStun(boolean stunned) {
		return;
	}


	@Override
	public void hurt(int damage,int deltaX,int deltaY) {
		if(dead) {
			return;
		}
		if(invulnerable<0) {
			return;
		}
		//shielding prevents damage
		if(!shielding) {
			setNeverDamaged(false);
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

		//knockback
		//no knockback when transformed and not shielding
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
		//set hitstun
		stunned=true;
		stunnedDuration=2;

	}

	//use secondary equipment
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

	//transform the player
	private void transform() {
		if(transformed) {
			speed = Creature.DEFAULT_SPEED;
			baseDamage = 1;
			bounds.y=32;
			bounds.height=32;

		}else {
			expandCheck();
			speed = Creature.DEFAULT_SPEED*0.7f;
			baseDamage = 2;
			bounds.y=16;
			bounds.height=48;
		}
		transformed = !transformed;
	}

	//get keyboard input
	private void getInput() {
		xMove=0;
		yMove=0;
		if(attacking>0) {
			return;
		}
		if(transformable&&handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			transform();
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
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
		//death animation
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

	//increment corruption meter when transformed
	public void corruptionTick() {
		if (transformed &&(corruption<corruptionMax)) {
			corruption+=2;
		}
		if(corruption >= corruptionMax) {
			corruption = corruptionMax;
			setTransformable(false);
			weapons.setPrimary(Weapons.Sword.OP);
		}

	}

	//calculate score and start death animation
	@Override
	public void die() {
		dead=true;
		HighScore highScore=new HighScore(handler);
		this.score+=inventory.getItemCount(1)*200+inventory.getItemCount(0)*50;
		highScore.checkHighScore(score);
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

	public boolean getTransformed(){
		return transformed;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean getShielding() {
		return shielding;
	}
}
