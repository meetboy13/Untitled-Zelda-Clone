package dev.game.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.game.Handler;
import dev.game.states.State;
import dev.launcher.Assets;

public class HUD {
	private int health,gems;
	private float corruption;
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
		this.corruption=handler.getWorld().getEntityManager().getPlayer().getCorruption();
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
		
		int x=100;
		Font f = new Font("Courier", Font.PLAIN,20);
		g.setColor(Color.BLACK);
		g.setFont(f);
		if(this.gems>0) {
			g.drawString(this.gems+"X", (int) (handler.getWidth()-x-((int)(Math.log10(this.gems))*12)), 20);
		}else {
			g.drawString(this.gems+"X", (int) (handler.getWidth()-x), 20);
		}
		g.drawImage(Assets.drop, handler.getWidth()-70, 0, 15,24,null);
		
		while(healthtemp>4) {
			g.drawImage(Assets.healthSpriteSheet[4], xOffset, 20, Assets.healthSpriteSheet[4].getWidth(), Assets.healthSpriteSheet[4].getHeight(),null);
			xOffset+=Assets.healthSpriteSheet[4].getWidth();
			healthtemp-=4;
		}
		
		g.drawImage(Assets.healthSpriteSheet[healthtemp], xOffset, 20, Assets.healthSpriteSheet[healthtemp].getWidth(), Assets.healthSpriteSheet[healthtemp].getHeight(),null);
		xOffset+=Assets.healthSpriteSheet[healthtemp].getWidth();
		
		int emptyslots=((handler.getWorld().getEntityManager().getPlayer().getMaxHealth())/4-(health-1)/4);
		while(emptyslots>0) {
			g.drawImage(Assets.healthSpriteSheet[0], xOffset, 20, Assets.healthSpriteSheet[0].getWidth(), Assets.healthSpriteSheet[0].getHeight(),null);
			xOffset+=Assets.healthSpriteSheet[0].getWidth();
			emptyslots--;
		}
		g.setColor(Color.gray);
		g.fillRect(0, 0, 200, 20);
		g.setColor(Color.BLACK);
		g.fillRect(0, 5, (int) (200*(corruption/handler.getWorld().getEntityManager().getPlayer().getCorruptionMax())), 10);
		
		
	}
}
