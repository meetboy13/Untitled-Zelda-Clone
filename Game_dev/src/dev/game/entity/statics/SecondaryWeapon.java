package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.inventory.Weapons.Equipment;

public class SecondaryWeapon extends StaticEntity{
	private Equipment drop;
	private boolean collide = false;
	public SecondaryWeapon(Handler handler, float x, float y, int width, int height, Equipment item) {
		super(handler, x, y, width, height);
		drop = item;
		solid = false;
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
		}else {
			collide = false;
		}
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
