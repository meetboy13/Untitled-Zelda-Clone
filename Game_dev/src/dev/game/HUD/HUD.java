package dev.game.HUD;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.states.State;
import dev.launcher.Assets;

public class HUD {
	private int health,gems,corruption;
	private Handler handler;
	public HUD (Handler handler) {
		this.handler=handler;
	}
	
	public void tick(){
		getHealth();
		getCorruption();
		getGems();
	}
	private void getGems() {
		// TODO Auto-generated method stub
		this.gems=handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(0);
	}

	private void getCorruption() {
		// TODO Auto-generated method stub
		this.corruption=0;
	}

	private void getHealth() {
		// TODO Auto-generated method stub
		this.health=handler.getWorld().getEntityManager().getPlayer().getHealth();
		if (health<0){
			health=0;
		}
	}

	public void render(Graphics g) {
		int xOffset=0;
		int healthtemp=health;
		while(healthtemp>4) {
			g.drawImage(Assets.healthSpriteSheet[4], xOffset, 0, Assets.healthSpriteSheet[4].getWidth(), Assets.healthSpriteSheet[4].getHeight(),null);
			xOffset+=Assets.healthSpriteSheet[4].getWidth();
			healthtemp-=4;
		}
		g.drawImage(Assets.healthSpriteSheet[healthtemp], xOffset, 0, Assets.healthSpriteSheet[healthtemp].getWidth(), Assets.healthSpriteSheet[healthtemp].getHeight(),null);
		xOffset+=Assets.healthSpriteSheet[healthtemp].getWidth();
		int emptyslots=((handler.getWorld().getEntityManager().getPlayer().getMaxHealth())/4-(health-1)/4);
		while(emptyslots>0) {
			g.drawImage(Assets.healthSpriteSheet[0], xOffset, 0, Assets.healthSpriteSheet[0].getWidth(), Assets.healthSpriteSheet[0].getHeight(),null);
			xOffset+=Assets.healthSpriteSheet[0].getWidth();
			emptyslots--;
		}
		
	}
}
