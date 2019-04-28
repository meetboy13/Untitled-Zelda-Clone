package dev.game.creatures;

import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;
import dev.game.Handler;
import dev.game.entity.projectile.WizardBeam;
import dev.game.item.Item;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Wizard2 extends Creature {

	private long lastAttackTimer,attackCooldown=2500,attackTimer=attackCooldown;
	private boolean aggressive = false;
	private boolean face=false;
	private boolean alwaysAggressive;
	private Animation animDown,animUp,animLeft,animRight, floatDown, floatUp, floatRight, floatLeft;
	private Facing lastDirection=Facing.DOWN;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Random rand = new Random();
	public Wizard2(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
		name="Wizard";
		health=8;
		id = 4;
		//animations
		animDown = new Animation(200,Assets.wizard2_down);
		animLeft = new Animation(200,Assets.wizard2_left);
		animUp = new Animation(200,Assets.wizard2_up);
		animRight = new Animation(200,Assets.wizard2_right);
		floatDown = new Animation(200,Assets.wizard2_float_down);
		floatLeft = new Animation(200,Assets.wizard2_float_left);
		floatUp = new Animation(200,Assets.wizard2_float_up);
		floatRight = new Animation(200,Assets.wizard2_float_right);

	}

	private void tickAnim() {
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
		tickAnim();
		getInput();
		stunDecay();
		move();
		aggression();
		checkAttacks();
	}

	//check is beam will be fired
	private void checkAttacks() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<attackCooldown) {
			return;
		}
		if (stunned) {
			return;
		}
		if(!aggressive && !alwaysAggressive) {
			return;
		}
		if((xMove!=0) || (yMove!=0)) {
			return;
		}

		//fire beam that travels in a cardinal direction
		WizardBeam attack;

		if(lastDirection==Facing.UP) {
			attack=new WizardBeam(handler,0,0);
			attack.setDirection(Direction.UP);
			attack.setX((float) (x+width/2-attack.getWidth()/2));
			attack.setY(this.getCollisionBounds(0, 0).y-attack.getHeight()/2-attack.getCollisionBounds(0, 0).height/2);
		}
		else if(lastDirection==Facing.DOWN) {
			attack=new WizardBeam(handler,0,0);
			attack.setDirection(Direction.DOWN);
			attack.setX((float) (x+width/2-attack.getWidth()/2));
			attack.setY((float) (this.getCollisionBounds(0, 0).y+this.bounds.height-attack.getBounds().getY()));
		}
		else if(lastDirection==Facing.LEFT) {
			attack=new WizardBeam(handler,0, 0);
			attack.setDirection(Direction.LEFT);			
			attack.setX(x-attack.getCollisionBounds(0, 0).width);
			attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
		}
		else if(lastDirection==Facing.RIGHT) {
			attack=new WizardBeam(handler,0,0);
			attack.setDirection(Direction.RIGHT);
			attack.setX((float) (this.getCollisionBounds(0, 0).x+bounds.x*2+bounds.width-this.bounds.height-attack.getBounds().getX())+15);
			attack.setY(this.getCollisionBounds(0, 0).y+bounds.height/2-attack.getHeight()/2);
		}else {
			return;
		}
		attackTimer = 0;
		handler.getWorld().getProjectileManager().addEntity(attack);
	}


	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);

	}
	//check if player is near
	public void aggression() {
		//circle detection of radius 300
		if ((Math.pow(x-handler.getWorld().getEntityManager().getPlayer().getX(), 2) + Math.pow(y-handler.getWorld().getEntityManager().getPlayer().getY(),2))<Math.pow(300, 2)){
			setAggressive(true);
		} else {
			setAggressive(false);
			face=true;
		}
	}

	//set movement
	private void getInput() {
		xMove = 0;
		yMove = 0;
		if(stunned) {
			return;
		}
		if (aggressive || alwaysAggressive) {
			//follow player
			float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
			float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

			if ((xDelta<20 && xDelta>-20)){
				if (face){//face the player before shooting
					face=false;
					if(yDelta>0) {
						yMove=-1;
					}else if(yDelta<0) {
						yMove=1;
					}
				}
			}else if((yDelta<20 && yDelta>-20)){
				if (face){//face the player before shooting
					face=false;
					if(xDelta>0) {
						xMove=-1;
					}else if(xDelta<0) {
						xMove=1;
					}
				}
			}else if (Math.abs(xDelta)<Math.abs(yDelta)) {//LOS with player is closest in x direction
				face=true;
				if(xDelta>0) {
					xMove=-speed;
				}else if(xDelta<0) {
					xMove=speed;
				}
			}else if (Math.abs(yDelta)<Math.abs(xDelta)) {//LOS with player is closest in y direction
				System.out.println("Ymatch");
				face=true;
				if(yDelta>0) {
					yMove=-speed;
				}else if(yDelta<0) {
					yMove=speed;
				}
			}
			//project attack code here
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
		active = false;
		handler.getWorld().getEntityManager().getPlayer().setScore(handler.getWorld().getEntityManager().getPlayer().getScore()+15);
		int xVar=(int) (rand.nextInt((int) this.getBounds().getWidth())-this.getBounds().getWidth()/2);
		int yVar=(int) (rand.nextInt((int) this.getBounds().getHeight())-this.getBounds().getHeight()/2);
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));
	}
	//set aggression
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
	}
	
	//get animation frame for rendering
	private BufferedImage getCurrentAnimationFrame() {
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
		return Assets.wizard2_down[1];
	}
}