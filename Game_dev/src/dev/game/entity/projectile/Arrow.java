package dev.game.entity.projectile;

import dev.game.Handler;
import dev.launcher.Assets;

public class Arrow extends Projectile{

	public Arrow(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		// TODO Auto-generated constructor stub
		bounds.x+=23;
		bounds.y+=5;
		bounds.height-=10;
		bounds.width-=23*2;
		texture=Assets.spear;
	}

}
