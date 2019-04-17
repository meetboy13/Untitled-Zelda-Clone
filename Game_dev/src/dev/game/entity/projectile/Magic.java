package dev.game.entity.projectile;

import dev.game.Handler;
import dev.game.worlds.World.Direction;
import dev.launcher.Assets;

public class Magic extends Projectile{

	public Magic(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x+=23;
		bounds.y+=5;
		bounds.height-=10;
		bounds.width-=23*2;
		texture=Assets.magic;
		speed = DEFAULT_PROJECTILE_SPEED/2;
	}
	/*@Override
	public void setDirection(Direction direction) {
		if(direction==Direction.UP) {
			yMove=-speed;
			texture=Assets.spear[1];
		}
		else if(direction==Direction.DOWN) {
			yMove=speed;
			texture=Assets.spear[3];
		}
		else if(direction==Direction.LEFT) {
			xMove=-speed;
			texture=Assets.spear[2];
		}
		else if(direction==Direction.RIGHT) {
			xMove=speed;
			texture=Assets.spear[0];
		}
	}*/
	
	public void setXSpeed(float xRatio) {
		xMove = xRatio*speed;
	}
	
	public void setYSpeed(float yRatio) {
		yMove = yRatio*speed;
	}
	
}
