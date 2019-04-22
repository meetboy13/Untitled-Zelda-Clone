package dev.game.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
	
import dev.game.Handler;
import dev.game.item.Item;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Sheep extends Creature {

	private Animation animDown,animUp,animLeft,animRight;
	private long lastMoveTimer,moveCooldown=1500,moveTimer=moveCooldown;
	private Random rand = new Random();
	public Sheep(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		name="sheep";
		id=1;
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED/4;
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
	@Override
	public void die() {
		// TODO Auto-generated method stub
		int xVar=rand.nextInt(64)-32;
		int yVar=rand.nextInt(64)-32;
		handler.getWorld().getItemManager().addItem(Item.healthPickup.createNewHealthPickup((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));
		active=false;
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
		return Assets.player_down[1];
	}
}
