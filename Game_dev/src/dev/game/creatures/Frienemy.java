package dev.game.creatures;

import java.awt.Graphics;
//import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.item.Item;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Frienemy extends Creature {

	private Animation animDown,animUp,animLeft,animRight;
	private Random rand = new Random();
	//private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	//private Random rand = new Random();
	public Frienemy(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, (int)(Creature.DEFAULT_CREATURE_WIDTH*1.3),(int)(Creature.DEFAULT_CREATURE_HEIGHT*1.3));
		// TODO Auto-generated constructor stub
		id=4;
		bounds.x=16;
		bounds.y=32;
		bounds.width=36;
		bounds.height=36;
		speed=(float) (Creature.DEFAULT_SPEED*.8);
		//animations
		animDown = new Animation(200,Assets.friend_down);
		animLeft = new Animation(200,Assets.friend_left);
		animUp = new Animation(200,Assets.friend_up);
		animRight = new Animation(200,Assets.friend_right);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		stunDecay();
		flickerDecay();
		if(!stunned) {
			getInput();
		}
		move();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (damageFlicker%20<15) {
			g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
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
		active=false;
		int xVar=(int) (rand.nextInt((int) this.getBounds().getWidth())-this.getBounds().getWidth()/2);
		int yVar=(int) (rand.nextInt((int) this.getBounds().getHeight())-this.getBounds().getHeight()/2);
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));
		
	}
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove<0) {
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
	}
}
