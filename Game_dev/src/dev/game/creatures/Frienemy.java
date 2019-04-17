package dev.game.creatures;

import java.awt.Graphics;
//import java.util.Random;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Frienemy extends Creature {

	private Animation animDown,animUp,animLeft,animRight;
	private Facing lastDirection=Facing.DOWN;
	//private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	//private Random rand = new Random();
	public Frienemy(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, (int)(Creature.DEFAULT_CREATURE_WIDTH*1.2),(int)(Creature.DEFAULT_CREATURE_HEIGHT*1.2));
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=32;
		bounds.width=36;
		bounds.height=36;
		speed=(float) (Creature.DEFAULT_SPEED*.8);
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
		stunDecay();
		if(!stunned) {
			getInput();
		}
		move();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
	}
	private void getInput() {
		if(handler.getWorld().getEntityManager().getPlayer().getX()>(x+5)) {
			xMove = speed;
		}else if(handler.getWorld().getEntityManager().getPlayer().getX()<(x-5)) {
			xMove = -speed;
		}else {
			xMove = 0;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getY()>(y+5)) {
			yMove = speed;
		}else if(handler.getWorld().getEntityManager().getPlayer().getY()<(y-5)) {
			yMove = -speed;
		}else {
			yMove = 0;
		}
	}
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
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
