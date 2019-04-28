package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.item.Item;
import dev.launcher.Assets;

public class Key extends StaticEntity{

	public Key(Handler handler, float x, float y) {
		super(handler, x, y, 50, 50);
		// TODO Auto-generated constructor stub
		solid = false;
		this.id = 10;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))) {
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(Item.key);
			die();
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.key,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
	}

	@Override
	public void die() {
		active = false;		
	}
	
	@Override
	public void hurt(int damage, int deltaX, int deltaY) {
		
	}
}
