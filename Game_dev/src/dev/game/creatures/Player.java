package dev.game.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.launcher.Animation;
import dev.launcher.Assets;

public class Player extends Creature{
	
	private Animation animDown,animUp,animLeft,animRight;
	
	public Player(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED;
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
		handler.getGameCamera().centeronEntity(this);
	}

	private void getInput() {
		xMove=0;
		yMove=0;
		if(handler.getKeyManager().up) {
			yMove= -speed;
		}
		if(handler.getKeyManager().down) {
			yMove= speed;
		}
		if(handler.getKeyManager().left) {
			xMove= -speed;
		}
		if(handler.getKeyManager().right) {
			xMove= speed;
		}
	}
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getCurrentAnimationFrame(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
		/* bounding box code, commented out in case needed for debugging later
		g.setColor(Color.blue);
		g.fillRect((int)(x+bounds.x-handler.getGameCamera().getxOffset()),(int)(y+bounds.y - handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
		*/
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove<0) {
			return animLeft.getCurrentFrame();
		}else if(xMove>0) {
			return animRight.getCurrentFrame();
		}else if (yMove<0) {
			return animUp.getCurrentFrame();
		}else if (yMove>0) {
			return animDown.getCurrentFrame();
		}
		//default animation to display if not condition is met.
		return Assets.player;
	}
	
}
