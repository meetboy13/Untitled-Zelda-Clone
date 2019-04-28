package dev.game.creatures;

import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;
import dev.game.Handler;
import dev.game.entity.projectile.WizardBeam;
import dev.game.item.Item;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Wizard extends Creature {

	private long lastAttackTimer = 0,attackCooldown=2000,attackTimer=attackCooldown;
	private boolean aggressive = false;
	//private boolean face=false;
	private boolean alwaysAggressive;
	private Animation animDown,animUp,animLeft,animRight, floatDown, floatUp, floatRight, floatLeft;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Random rand = new Random();
	//constructor
	public Wizard(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		id=3;
		bounds.x=16;
		bounds.y=18;
		bounds.width=28;
		bounds.height=42;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
		name="Wizard";
		health=9;
		//animations
		animDown = new Animation(200,Assets.wizard_down);
		animLeft = new Animation(200,Assets.wizard_left);
		animUp = new Animation(200,Assets.wizard_up);
		animRight = new Animation(200,Assets.wizard_right);
		floatDown = new Animation(200,Assets.wizard_float_down);
		floatLeft = new Animation(200,Assets.wizard_float_left);
		floatUp = new Animation(200,Assets.wizard_float_up);
		floatRight = new Animation(200,Assets.wizard_float_right);

	}

	private void tickAnimations() {
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		floatDown.tick();
		floatUp.tick();
		floatRight.tick();
		floatLeft.tick();
	}

	@Override
	public void tick() {
		tickAnimations();
		flickerDecay();
		getInput();
		stunDecay();
		move();
		if (!alwaysAggressive) {
			aggression();
		}
	}

	//check aggression to see if attacking
	private void checkAttacks() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<attackCooldown) {
			return;
		}
		if(!aggressive && !alwaysAggressive) {
			return;
		}
		attackTimer = 0;

		//spawn beam that is targeted at player
		float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
		float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

		float xRatio = Math.abs(xDelta)/(Math.abs(xDelta)+Math.abs(yDelta));
		float yRatio = Math.abs(yDelta)/(Math.abs(xDelta)+Math.abs(yDelta));

		WizardBeam attack;

		if(lastDirection==Facing.UP) {
			attack=new WizardBeam(handler,0,0);
			attack.setX((float) (x+width/2-attack.getWidth()/2));
			attack.setY(this.getCollisionBounds(0, 0).y-attack.getHeight()/2-attack.getCollisionBounds(0, 0).height/2);
		}
		else if(lastDirection==Facing.DOWN) {
			attack=new WizardBeam(handler,0,0);
			attack.setX((float) (x+width/2-attack.getWidth()/2));
			attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()));
		}
		else if(lastDirection==Facing.LEFT) {
			attack=new WizardBeam(handler,0, 0);
			attack.setX(x-attack.getCollisionBounds(0, 0).width);
			attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
		}
		else if(lastDirection==Facing.RIGHT) {
			attack=new WizardBeam(handler,0,0);
			attack.setX((float) (this.getCollisionBounds(0, 0).x+bounds.x*2+bounds.width-this.bounds.height-attack.getBounds().getX())+15);
			attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
		}else {
			return;
		}

		if(xDelta>0) {
			attack.setXSpeed(-xRatio);
		}else {
			attack.setXSpeed(xRatio);
		}

		if(yDelta>0) {
			attack.setYSpeed(-yRatio);
		}else {
			attack.setYSpeed(yRatio);
		}

		handler.getWorld().getProjectileManager().addEntity(attack);

	}


	@Override
	public void render(Graphics g) {
		if (damageFlicker%20<15) {
			g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
	}

	//check if player is near enemy
	public void aggression() {
		//circle detection of radius 400
		if ((Math.pow(x-handler.getWorld().getEntityManager().getPlayer().getX(), 2) + Math.pow(y-handler.getWorld().getEntityManager().getPlayer().getY(),2))<Math.pow(400, 2)){
			setAggressive(true);
		} else {
			setAggressive(false);
		}
	}


	private void getInput() {
		xMove = 0;
		yMove = 0;

		if(stunned) {
			return;
		}
		//attack
		if (aggressive || alwaysAggressive) {
			checkAttacks();
		} else {
			//move randomly
			moveTimer+=System.currentTimeMillis()-lastMoveTimer;
			lastMoveTimer=System.currentTimeMillis();
			if(moveTimer>=moveCooldown) {
				moveTimer=0;
				switch (rand.nextInt(5)) {
				//up
				case 0:
					xMove=0;
					yMove=speed;
					break;
					//down
				case 1:
					xMove=0;
					yMove=-speed;
					break;
					//left
				case 2:
					xMove=-speed;
					yMove=0;
					break;
					//right
				case 3:
					xMove=speed;
					yMove=0;
					break;
					//stop
				case 4:
					xMove=0;
					yMove=0;
					break;
				}
			}
		}
	}
	//add to score and drop gem
	@Override
	public void die() {
		handler.getWorld().getEntityManager().getPlayer().setScore(handler.getWorld().getEntityManager().getPlayer().getScore()+100);
		active=false;
		int xVar=(int) (rand.nextInt((int) this.getBounds().getWidth())-this.getBounds().getWidth()/2);
		int yVar=(int) (rand.nextInt((int) this.getBounds().getHeight())-this.getBounds().getHeight()/2);
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));

	}

	//get animation frame for rendering
	private BufferedImage getCurrentAnimationFrame() {
		if (aggressive) {
			float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
			float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

			if (Math.abs(yDelta)<Math.abs(xDelta)) {
				if(xDelta<0) {
					lastDirection=Facing.RIGHT;
					return Assets.wizard_right[1];
				}else {
					lastDirection=Facing.LEFT;
					return Assets.wizard_left[1];				
				} 
			}else {
				if(yDelta>0) {
					lastDirection=Facing.UP;
					return Assets.wizard_up[1];
				}else {
					lastDirection=Facing.DOWN;
					return Assets.wizard_down[1];

				}
			}
		}else {
			if(xMove<0) {
				lastDirection=Facing.LEFT;
				return floatLeft.getCurrentFrame();
			}else if(xMove>0) {
				lastDirection=Facing.RIGHT;
				return floatRight.getCurrentFrame();
			}else if (yMove<0) {
				lastDirection=Facing.UP;
				return floatUp.getCurrentFrame();
			}else if (yMove>0) {
				lastDirection=Facing.DOWN;
				return floatDown.getCurrentFrame();
			}else if (lastDirection==Facing.LEFT) {
				return animLeft.getCurrentFrame();	
			}
			else if (lastDirection==Facing.RIGHT) {
				return animRight.getCurrentFrame();
			}
			else if (lastDirection==Facing.UP) {
				return animUp.getCurrentFrame();
			}
			else if (lastDirection==Facing.DOWN) {
				return animDown.getCurrentFrame();
			}
			//default animation to display if not condition is met.
			return Assets.wizard_down[1];
		}
	}

	//setter
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
	}
}
