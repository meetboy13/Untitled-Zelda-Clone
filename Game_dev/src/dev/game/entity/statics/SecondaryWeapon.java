package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.inventory.Weapons.Equipment;
import dev.launcher.Assets;

public class SecondaryWeapon extends StaticEntity{
	private Equipment drop;
	private int location;
	private boolean collide = false;
	public SecondaryWeapon(Handler handler, float x, float y, int width, int height, Equipment item, int world,int id) {
		super(handler, x, y, width, height);
		drop = item;
		solid = false;
		location = world;
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(drop == Equipment.none) {
			active = false;
			return;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))&&(!collide)) {
			collide = true;
			Equipment temp = drop;
			drop = handler.getWorld().getEntityManager().getPlayer().getWeapons().getSecondary();
			handler.getWorld().getEntityManager().getPlayer().getWeapons().setSecondary(temp);
			if (location == 1) {
				handler.getWorld().setWorld1Secondary(drop);
			}else if (location == 2) {
				handler.getWorld().setWorld2Secondary(drop);
			}
		}else if(!handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.getCollisionBounds(0, 0))){
			collide = false;
		}
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (drop == Equipment.javelin) {
			g.drawImage(Assets.spear[0],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}else if (drop == Equipment.shield) {
			g.drawImage(Assets.shield,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}else if(drop == Equipment.wand) {
			g.drawImage(Assets.wand,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}
			g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		active=true;
		this.health=10;
	}

}
