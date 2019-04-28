package dev.game.item;


import dev.launcher.Assets;

public class HealthPickup extends Item{
	private int heal=4;
	public HealthPickup(String name, int id) {
		super(Assets.health, name, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			System.out.println(handler.getWorld().getEntityManager().getPlayer().getHealth());
			handler.getWorld().getEntityManager().getPlayer().setHealth( (handler.getWorld().getEntityManager().getPlayer().getHealth()+heal ));
			System.out.println(handler.getWorld().getEntityManager().getPlayer().getHealth());
			pickedUp=true;
		}
	}
}
