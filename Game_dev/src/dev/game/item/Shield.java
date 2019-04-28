package dev.game.item;

import java.awt.image.BufferedImage;

import dev.game.inventory.Weapons.Equipment;

public class Shield extends Item{

	public Shield(BufferedImage texture, String name, int id) {
		super(texture, name, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void  tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp=true;
			handler.getWorld().getEntityManager().getPlayer().getWeapons().setSecondary(Equipment.shield);;
		}
	}
	@Override
	public Shield createNew(int x,int y) {
		Shield i = new Shield(texture,name,id);
		i.setPosition(x, y);
		return i;
	}
	
}
