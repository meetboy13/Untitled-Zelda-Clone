package dev.game.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.entity.Entity;
import dev.game.entity.projectile.Arrow;
import dev.game.entity.projectile.WizardBeam;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Wizard2 extends Creature {

	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	private boolean aggressive = false;
	private boolean face=false;
	private boolean alwaysAggressive;
	private Animation animDown,animUp,animLeft,animRight, floatDown, floatUp, floatRight, floatLeft;
	private Facing lastDirection=Facing.DOWN;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Random rand = new Random();
	public Wizard2(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
		name="Wizard";
		health=10;
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

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		if(!stunned) {
			getInput();
		}
		stunDecay();
		move();
		aggression();
		checkAttacks();
	}

	
	private void checkAttacks() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<attackCooldown) {
			return;
		}
		// TODO Auto-generated method stub
		if(!aggressive && !alwaysAggressive) {
			return;
		}
		if((xMove!=0) || (yMove!=0)) {
			return;
		}

		if(lastDirection==Facing.UP) {
			WizardBeam attack=new WizardBeam(handler, x+width/2-Arrow.DEFAULT_PROJECTILE_WIDTH/2, (int)(y-Arrow.DEFAULT_PROJECTILE_HEIGHT/4));
			attack.setDirection(Direction.UP);
			handler.getWorld().getProjectileManager().addEntity(attack);
		}
		else if(lastDirection==Facing.DOWN) {
			WizardBeam attack=new WizardBeam(handler, x+width/2-Arrow.DEFAULT_PROJECTILE_WIDTH/2, (int)(y+height+Arrow.DEFAULT_PROJECTILE_HEIGHT/4));
			attack.setDirection(Direction.DOWN);
			handler.getWorld().getProjectileManager().addEntity(attack);
		}
		else if(lastDirection==Facing.LEFT) {
			WizardBeam attack=new WizardBeam(handler, (int)(x-Arrow.DEFAULT_PROJECTILE_WIDTH/4), y+height/2-Arrow.DEFAULT_PROJECTILE_HEIGHT/2);
			attack.setDirection(Direction.LEFT);
			handler.getWorld().getProjectileManager().addEntity(attack);
		}
		else if(lastDirection==Facing.RIGHT) {
			WizardBeam attack=new WizardBeam(handler,(int)(x+width+Arrow.DEFAULT_PROJECTILE_WIDTH/4), y+height/2-Arrow.DEFAULT_PROJECTILE_HEIGHT/2);
			attack.setDirection(Direction.RIGHT);
			handler.getWorld().getProjectileManager().addEntity(attack);
		}else {
			return;
		}
		
	}

	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
	}
	public void aggression() {
		//square detection
		//if(((x-200) < handler.getWorld().getEntityManager().getPlayer().getX()) && (handler.getWorld().getEntityManager().getPlayer().getX() <(x+200))&&(((y-200) < handler.getWorld().getEntityManager().getPlayer().getY()) && (handler.getWorld().getEntityManager().getPlayer().getY() <(y+200)))) {
		//circle detection of radius 300
		if ((Math.pow(x-handler.getWorld().getEntityManager().getPlayer().getX(), 2) + Math.pow(y-handler.getWorld().getEntityManager().getPlayer().getY(),2))<Math.pow(300, 2)){
			setAggressive(true);
		} else {
			setAggressive(false);
			face=true;
		}
	}
	private void getInput() {
		if (aggressive || alwaysAggressive) {
			xMove = 0;
			yMove = 0;
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
	@Override
	public void die() {
		// TODO Auto-generated method stub
		System.out.println("The WIZARD DIED");
		
	}
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
	}
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
		return Assets.wizard_down[1];
	}
}