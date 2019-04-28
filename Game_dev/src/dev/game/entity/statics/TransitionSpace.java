package dev.game.entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.worlds.World.Direction;
import dev.game.worlds.World.WorldType;
import dev.launcher.Assets;

public class TransitionSpace extends StaticEntity{
	private String pathEntity,pathWorld;
	private WorldType worldType=WorldType.NORMAL;
	private BufferedImage texture;
	public TransitionSpace(Handler handler,float x ,float y, int width, int height, Direction direction, int id,String pathWorld,String pathEntity,WorldType worldType) {
		super(handler, x, y, width, height);
		this.id=id;
		this.pathEntity=pathEntity;
		this.pathWorld=pathWorld;
		this.solid=false;
		this.worldType=worldType;
		if (direction==Direction.UP){
			texture=Assets.transition[0];
		}else if (direction==Direction.LEFT){
			texture=Assets.transition[2];
		}else if (direction==Direction.RIGHT){
			texture=Assets.transition[1];	
		}else if (direction==Direction.DOWN){
			texture=Assets.transition[3];
		}else if (direction==Direction.NULL){
			texture=Assets.transition[4];	
		}
	}	

	@Override
	public void hurt(int damage, int deltaX, int deltaY) {
		// Not get damaged
	}

	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(1)<2 && this.id==95) {
		}else if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))) {
			active=false;
			handler.getWorld().getEntityManager().getPlayer().setX((handler.getWorld().getEntityManager().getPlayer().getX())-2*(handler.getWorld().getEntityManager().getPlayer().getxMove()));
			handler.getWorld().getEntityManager().getPlayer().setY((handler.getWorld().getEntityManager().getPlayer().getY())-2*(handler.getWorld().getEntityManager().getPlayer().getyMove()));
			handler.getWorld().saveWorld();
			handler.getWorld().getEntityManager().clear1();
			handler.getWorld().getProjectileManager().clear2();
			handler.getWorld().getItemManager().clear();
			handler.getWorld().setWorldType(worldType);
			handler.getWorld().loadNewWorld(pathWorld, pathEntity);
		}
	}
	@Override
	public void render(Graphics g) {

		if(handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(1)<2 && this.id==95) {}
		else {
			g.drawImage(texture,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
	}

	@Override
	public void die() {
		// don't become inactive
	}

}
