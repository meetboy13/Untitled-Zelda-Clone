package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.entity.Entity;
import dev.game.entity.projectile.Arrow;
import dev.game.entity.projectile.WizardBeam;
import dev.game.item.Item;
import dev.game.worlds.World.Direction;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Wizard extends Creature {

	private long lastAttackTimer = 0,attackCooldown=1000,attackTimer=attackCooldown;
	private boolean aggressive = false;
	private boolean face=false;
	private boolean alwaysAggressive;
	private Animation animDown,animUp,animLeft,animRight;
	private Facing lastDirection=Facing.DOWN;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Random rand = new Random();
	public Wizard(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=28;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
		name="Wizard";
		health=10;
		//animations
		animDown = new Animation(200,Assets.player_down);
		animLeft = new Animation(200,Assets.player_left);
		animUp = new Animation(200,Assets.player_up);
		animRight = new Animation(200,Assets.player_right);

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
		if (!alwaysAggressive) {
			aggression();
		}
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
		//if((xMove!=0) || (yMove!=0)) {
		//	return;
		//}
		attackTimer = 0;
		
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
			attack.setX((float) (this.getCollisionBounds(0, 0).x+bounds.x*2+bounds.width-this.bounds.height-attack.getBounds().getX()));
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
		// TODO Auto-generated method stub
		g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		/*
		Code to show wizard hitboxes
		g.setColor(Color.BLACK);
		g.drawRect((int)(x+bounds.x-handler.getGameCamera().getxOffset()),(int)(y+bounds.y-handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
		*/
	}
	public void aggression() {
		//square detection
		//if(((x-200) < handler.getWorld().getEntityManager().getPlayer().getX()) && (handler.getWorld().getEntityManager().getPlayer().getX() <(x+200))&&(((y-200) < handler.getWorld().getEntityManager().getPlayer().getY()) && (handler.getWorld().getEntityManager().getPlayer().getY() <(y+200)))) {
		//circle detection of radius 400
		if ((Math.pow(x-handler.getWorld().getEntityManager().getPlayer().getX(), 2) + Math.pow(y-handler.getWorld().getEntityManager().getPlayer().getY(),2))<Math.pow(400, 2)){
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
			//float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
			//float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

			/*
			if ((xDelta < 15 && xDelta > -15)){
				if (face){//face the player before shooting
					face=false;
					if(yDelta>0) {
						yMove=-1;
					}else if(yDelta<0) {
						yMove=1;
					}
				}
			}else if((yDelta<15 && yDelta>-15)){
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
			}*/
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
		active=false;
		int xVar=rand.nextInt(128)-64;
		int yVar=rand.nextInt(128)-64;
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));

	}
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
	}
	private BufferedImage getCurrentAnimationFrame() {
		if (aggressive) {
			float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
			float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

			if (Math.abs(yDelta)<Math.abs(xDelta)) {
				if(xDelta<0) {
					lastDirection=Facing.RIGHT;
					return Assets.player_right[1];
				}else {
					lastDirection=Facing.LEFT;
					return Assets.player_left[1];				} 
			}else {
				if(yDelta>0) {
					lastDirection=Facing.UP;
					return Assets.player_up[1];
				}else {
					lastDirection=Facing.DOWN;
					return Assets.player_down[1];

				}
			}
		}else {
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
	}
}
