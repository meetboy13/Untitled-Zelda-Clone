package dev.game.item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.entity.statics.StaticEntity;
import dev.launcher.Assets;

public class TransitionSpace extends StaticEntity{
	private String pathEntity,pathWorld;
	public TransitionSpace(Handler handler,float x ,float y, int width, int height, int id,String pathWorld,String pathEntity) {
		super(handler, x, y, 64, 64);
		this.id=id;
		this.pathEntity=pathEntity;
		this.pathWorld=pathWorld;
		this.solid=false;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void die() {
		//add random location variability
		health=10;
		active=true;
	}
	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))) {
			active=false;
			handler.getWorld().saveWorld();
			handler.getWorld().getEntityManager().clear1();
			handler.getWorld().getProjectileManager().clear2();
			handler.getWorld().getItemManager().clear();
			handler.getWorld().loadNewWorld(pathWorld, pathEntity);
		}
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.drop,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		g.setColor(Color.BLUE);
		g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
		
	}

}
