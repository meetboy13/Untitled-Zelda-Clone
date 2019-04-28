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

public class Bull extends Creature {

	private boolean aggressive = false;
	private boolean face=false;
	private boolean alwaysAggressive;
	private boolean attacking = false;
	private Animation animDown,animUp,animLeft,animRight;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Rectangle cb =this.getCollisionBounds(0,0);
	private Rectangle ar= new Rectangle();
	private Random rand = new Random();
	public Bull(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		id=6;
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
		name="Bull";
		health=10;
		//animations
		animDown = new Animation(200,Assets.bull_down);
		animLeft = new Animation(200,Assets.bull_left);
		animUp = new Animation(200,Assets.bull_up);
		animRight = new Animation(200,Assets.bull_right);

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		flickerDecay();
		getInput();
		move();
		if(stunned) {
			aggression();
			checkAttacks();
		}
		stunDecay();
	}


	private void checkAttacks() {
		// TODO Auto-generated method stub
		if (stunned) {return;}
		if(!aggressive && !alwaysAggressive) {
			return;
		}
		if(!face) {
			return;
		}
		if(xMove==1||xMove==-1||yMove==-1||yMove==1) {
			return;
		}
		attacking = true;

		cb =this.getCollisionBounds(0,0);
		ar= new Rectangle();
		int arSize=20;
		ar.width=arSize;
		ar.height=arSize;
		xMove=0;
		yMove=0;
		if(lastDirection==Facing.UP) {
			yMove = -4;
			ar.x=cb.x+cb.width/2-arSize/2;
			ar.y=cb.y-arSize;
		}
		else if(lastDirection==Facing.DOWN) {
			yMove = 4;
			ar.x=cb.x+cb.width/2-arSize/2;
			ar.y=cb.y+cb.height;
		}
		else if(lastDirection==Facing.LEFT) {
			xMove = -4;
			ar.x=cb.x-arSize;
			ar.y=cb.y+cb.height/2-arSize/2;
		}
		else if(lastDirection==Facing.RIGHT) {
			xMove = 4;
			ar.x=cb.x+cb.width;
			ar.y=cb.y+cb.height/2-arSize/2;
		}
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
				int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
				e.hurt(damage,deltaX,deltaY);
				attacking=false;
				stunned=true;
				stunnedDuration=150;
			}
		}
		
	}


	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (damageFlicker%20<15) {
			g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
	}
	public void aggression() {
		//square detection
		//if(((x-200) < handler.getWorld().getEntityManager().getPlayer().getX()) && (handler.getWorld().getEntityManager().getPlayer().getX() <(x+200))&&(((y-200) < handler.getWorld().getEntityManagerbullPlayer().getY()) && (handler.getWorld().getEntityManager().getPlayer().getY() <(y+200)))) {
		//circle detection of radius 400
		if ((Math.pow(x-handler.getWorld().getEntityManager().getPlayer().getX(), 2) + Math.pow(y-handler.getWorld().getEntityManager().getPlayer().getY(),2))<Math.pow(250, 2)){
			setAggressive(true);
		} else {
			setAggressive(false);
			face=false;
			attacking = false;
		}
	}
	private void getInput() {
		if(stunned) {
			xMove = 0;
			yMove = 0;
			attacking = false;
			aggressive = false;
			return;
		}
		if ((aggressive || alwaysAggressive) && !attacking) {
			xMove = 0;
			yMove = 0;
			float xDelta = x-handler.getWorld().getEntityManager().getPlayer().getX();
			float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();

			
			if (Math.abs(xDelta) < 15){
				if (!face){//face the player before shooting
					face=true;
					if(yDelta>0) {
						yMove=-1;
					}else if(yDelta<0) {
						yMove=1;
					}
				}
			}else if(Math.abs(yDelta)<15){
				if (!face){//face the player before shooting
					face=true;
					if(xDelta>0) {
						xMove=-1;
					}else if(xDelta<0) {
						xMove=1;
					}
				}
			}else if (Math.abs(xDelta)<Math.abs(yDelta)) {//LOS with player is closest in x direction
				face=false;
				if(xDelta>0) {
					xMove=-speed;
				}else if(xDelta<0) {
					xMove=speed;
				}
			}else if (Math.abs(yDelta)<Math.abs(xDelta)) {//LOS with player is closest in y direction
				face=false;
				if(yDelta>0) {
					yMove=-speed;
				}else if(yDelta<0) {
					yMove=speed;
				}
			}
		} else if(attacking) {
			return;
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
		handler.getWorld().getEntityManager().getPlayer().setScore(handler.getWorld().getEntityManager().getPlayer().getScore()+100);

	}
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
	}
	private BufferedImage getCurrentAnimationFrame() {
			if (stunned) {
				if(lastDirection==Facing.LEFT) {
					return Assets.bull_stunned[1];
				}else if(lastDirection==Facing.RIGHT) {
					return Assets.bull_stunned[2];
				}else if(lastDirection==Facing.DOWN) {
					return Assets.bull_stunned[0];
				}else {
					return Assets.bull_stunned[3];
				}
			}else if(xMove<0) {
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
				return Assets.bull_left[1];
			}
			else if (lastDirection==Facing.RIGHT) {
				return Assets.bull_right[1];
			}
			else if (lastDirection==Facing.UP) {
				return Assets.bull_up[1];
			}
			else if (lastDirection==Facing.DOWN) {
				return Assets.bull_down[1];
			}
			//default animation to display if not condition is met.
			return Assets.bull_down[1];
	}
}
