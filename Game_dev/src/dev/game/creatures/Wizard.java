package dev.game.creatures;

import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Wizard extends Creature {

	private boolean aggressive = false;
	private boolean alwaysAggressive;
	private Animation animDown,animUp,animLeft,animRight;
	private Facing lastDirection=Facing.DOWN;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Random rand = new Random();
	public Wizard(Handler handler, float x, float y, int width, int height, boolean fixedAggre) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/6;
		alwaysAggressive = fixedAggre;
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
		getInput();
		move();
		aggression();
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
		}
	}
	private void getInput() {
		if (aggressive || alwaysAggressive) {
			xMove = 0;
			yMove = 0;
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
		
	}
	public void setAggressive(boolean aggro) {
		aggressive = aggro;
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
}
