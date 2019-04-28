package dev.game.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.game.inventory.Weapons.Equipment;
import dev.game.inventory.Weapons.Sword;
import dev.game.states.State;
import dev.launcher.Assets;

public class HUD {
	private int health,gems,keys;
	private float corruption;
	private Handler handler;
	private long now;
	private long lastTime = System.nanoTime();
	private long timer =0;
	private int timeLimit=300,mins,secs;
	private BufferedImage primary=Assets.sword_training, secondary=null;
	public HUD (Handler handler) {
		this.handler=handler;
	}

	public void tick(){
		getHealth();
		getCorruption();
		getGems();
		getKeys();
		timer();
		getPrimary();
		getSecondary();
	}
	

	private void getKeys() {
		// TODO Auto-generated method stub
		this.keys=handler.getWorld().getEntityManager().getPlayer().getInventory().getItemCount(1);
	}

	private void getSecondary() {
		if (handler.getWorld().getEntityManager().getPlayer().getWeapons().getSecondary()==Equipment.javelin){
			secondary=Assets.spear[1];
		}else if (handler.getWorld().getEntityManager().getPlayer().getWeapons().getSecondary()==Equipment.shield){
			secondary=Assets.shield;
		}else if (handler.getWorld().getEntityManager().getPlayer().getWeapons().getSecondary()==Equipment.wand){
			secondary=Assets.wand;
		}else {
			//nothing
		}
	}

	private void getPrimary() {
		if(handler.getWorld().getEntityManager().getPlayer().getWeapons().getPrimary()==Sword.training) {
			primary=Assets.sword_training;
		}
		else if(handler.getWorld().getEntityManager().getPlayer().getWeapons().getPrimary()==Sword.mirror) {
			primary=Assets.sword_mirror;
		}
		else if(handler.getWorld().getEntityManager().getPlayer().getWeapons().getPrimary()==Sword.OP) {
			primary=Assets.sword_op;
		}
	}

	private void timer() {
		if (timeLimit == 0) {
			return;
		}
		now = System.nanoTime();
		timer += now-lastTime;
		lastTime=now;
		if (timer >= 1000000000){
			timer=0;
			timeLimit--;
			/*if (timeLimit<0) {
				timeLimit=0;
			}*/
		}
		mins=timeLimit/60;
		secs=timeLimit%60;

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

		//timer
		int x=100;
		Font f = new Font("Courier", Font.PLAIN,20);
		g.setColor(Color.BLACK);
		g.setFont(f);
		if(this.secs<=0) {
			g.drawString(this.mins+":"+this.secs+ "0", (int) (handler.getWidth()-100), 40);
		}else if(this.secs<10) {
			g.drawString(this.mins+":"+ "0"+this.secs, (int) (handler.getWidth()-100), 40);
		}else {
			g.drawString(this.mins+":"+this.secs, (int) (handler.getWidth()-100), 40);
		}
		
		//weapons
		g.setColor(Color.GREEN);
		g.fillOval(handler.getWidth()/2-56, 0, 62, 62);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(handler.getWidth()/2-55, 1, 60, 60);
		g.drawImage(primary, handler.getWidth()/2-55, 6, 60,60,null);

		g.setColor(Color.GREEN);
		g.fillOval(handler.getWidth()/2+54, 0, 62, 62);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(handler.getWidth()/2+55, 1, 60, 60);
		g.drawImage(secondary, handler.getWidth()/2+60, 11,50,50,null);
		
		//keys
		g.setColor(Color.BLACK);
		g.drawImage(Assets.key, handler.getWidth()-15, 0, 15,24,null);
		if(this.keys>0) {
			g.drawString(this.keys+"X", (int) (handler.getWidth()-40-((int)(Math.log10(this.keys))*12)), 20);
		}else {
			g.drawString(this.keys+"X", (int) (handler.getWidth()-40), 20);
		}
		g.drawImage(Assets.key, handler.getWidth()-15, 0, 15,24,null);
				
		//gems
		g.setColor(Color.BLACK);
		g.drawImage(Assets.drop, handler.getWidth()-70, 0, 15,24,null);
		if(this.gems>0) {
			g.drawString(this.gems+"X", (int) (handler.getWidth()-x-((int)(Math.log10(this.gems))*12)), 20);
		}else {
			g.drawString(this.gems+"X", (int) (handler.getWidth()-x), 20);
		}
		g.drawImage(Assets.drop, handler.getWidth()-70, 0, 15,24,null);
		
		//health
		if (handler.getWorld().getEntityManager().getPlayer().isNeverDamaged()) {
			while(healthtemp>0) {
				g.drawImage(Assets.healthimproved, xOffset, 20, Assets.healthimproved.getWidth(), Assets.healthimproved.getHeight(),null);
				xOffset+=Assets.healthimproved.getWidth();
				healthtemp-=4;
			}
		}else {
			while(healthtemp>4) {
				g.drawImage(Assets.healthSpriteSheet[4], xOffset, 20, Assets.healthSpriteSheet[4].getWidth(), Assets.healthSpriteSheet[4].getHeight(),null);
				xOffset+=Assets.healthSpriteSheet[4].getWidth();
				healthtemp-=4;
			}

			if (healthtemp > 0) {
				g.drawImage(Assets.healthSpriteSheet[healthtemp], xOffset, 20, Assets.healthSpriteSheet[healthtemp].getWidth(), Assets.healthSpriteSheet[healthtemp].getHeight(),null);
				xOffset+=Assets.healthSpriteSheet[healthtemp].getWidth();
			}

			int emptyslots=((handler.getWorld().getEntityManager().getPlayer().getMaxHealth()-health)/4);
			while(emptyslots>0) {
				g.drawImage(Assets.healthSpriteSheet[0], xOffset, 20, Assets.healthSpriteSheet[0].getWidth(), Assets.healthSpriteSheet[0].getHeight(),null);
				xOffset+=Assets.healthSpriteSheet[0].getWidth();
				emptyslots--;
			}
		}
		
		//corruption
		g.setColor(Color.gray);
		g.fillRect(0, 0, 200, 20);
		g.setColor(Color.BLACK);
		g.fillRect(0, 5, (int) (200*(corruption/handler.getWorld().getEntityManager().getPlayer().getCorruptionMax())), 10);


	}
}
