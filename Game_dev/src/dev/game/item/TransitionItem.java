package dev.game.item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class TransitionItem extends Item{
	private String pathEntity,pathWorld;
	public TransitionItem(String name, int id,String pathWorld,String pathEntity) {
		super(Assets.drop,name, id);
		width=64;
		height=64;
		this.pathEntity=pathEntity;
		this.pathWorld=pathWorld;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp=true;
			handler.getWorld().getEntityManager().clear();
			handler.getWorld().getItemManager().clear();
			handler.getWorld().loadNewWorld(pathWorld, pathEntity);
		}
	}
	@Override
	public void render(Graphics g,int x, int y) {
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
		g.setColor(Color.BLUE);
		g.drawRect(x, y, ITEMWIDTH, ITEMHEIGHT);
	}

}
