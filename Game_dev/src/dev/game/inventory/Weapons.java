package dev.game.inventory;

import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.inventory.Inventory.Equipment;
import dev.game.inventory.Inventory.Sword;
import dev.game.worlds.World.Direction;

public class Weapons {
	public enum Equipment{shield,spear,wand,none};
	private Equipment secondary;

	public enum Sword{training,mirror,OP};
	private Sword primary;
	
	private int damagePrimary=10,damageSecondary=1
			,primaryCooldown=500,secondaryCooldown=0;
	public Weapons(Handler handler) {
		primary= Sword.training;
		secondary = Equipment.none;
	}
	
	public Rectangle getHitBox(Facing direction,Rectangle cb) {
		Rectangle ar = null;
		ar= new Rectangle();
		int arSize=20;
		ar.width=arSize;
		ar.height=arSize;
		if(direction==Facing.UP) {
			ar.x=cb.x;
			ar.y=cb.y-arSize;
			ar.width=cb.width;
		}
		else if(direction==Facing.DOWN) {
			ar.x=cb.x;
			ar.y=cb.y+cb.height;
			ar.width=cb.width;
		}
		else if(direction==Facing.LEFT) {
			ar.x=cb.x-arSize;
			ar.y=cb.y;
			ar.height=cb.height;
		}
		else if(direction==Facing.RIGHT) {
			ar.x=cb.x+cb.width;
			ar.y=cb.y;
			ar.height=cb.height;
		}else {
			return new Rectangle();
		}
		return ar;
	}
	
	public Equipment getSecondary() {
		return secondary;
	}
	public void setSecondary(Equipment secondary) {
		this.secondary = secondary;
	}
	public Sword getPrimary() {
		return primary;
	}
	public void setPrimary(Sword primary) {
		this.primary = primary;
		if(this.primary==Sword.training) {
			damagePrimary=0;
			primaryCooldown=500;
		}
		else if(this.primary==Sword.mirror) {
			damagePrimary=1;
			primaryCooldown=500;
		}
		else if(this.primary==Sword.OP) {
			damagePrimary=3;
			primaryCooldown=500;
		}
	}
	public int getDamagePrimary() {
		return damagePrimary;
	}
	public void setDamagePrimary(int damagePrimary) {
		this.damagePrimary = damagePrimary;
	}
	public int getDamageSecondary() {
		return damageSecondary;
	}
	public void setDamageSecondary(int damageSecondary) {
		this.damageSecondary = damageSecondary;
	}
	public int getPrimaryCooldown() {
		return primaryCooldown;
	}
	public void setPrimaryCooldown(int primaryCooldown) {
		this.primaryCooldown = primaryCooldown;
	}
	public int getSecondaryCooldown() {
		return secondaryCooldown;
	}
	public void setSecondaryCooldown(int secondaryCooldown) {
		this.secondaryCooldown = secondaryCooldown;
	}
	
	
}
