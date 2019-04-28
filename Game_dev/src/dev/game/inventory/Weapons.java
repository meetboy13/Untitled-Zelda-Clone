package dev.game.inventory;

import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.creatures.Creature.Facing;
import dev.game.item.Item;
import dev.game.worlds.World.Direction;

public class Weapons {
	public enum Equipment{shield,javelin,wand,none};
	private Equipment secondary;

	public enum Sword{training,mirror,OP};
	private Sword primary;
	private Handler handler;
	private int damagePrimary=5,damageSecondary=1
			,primaryCooldown=500,secondaryCooldown=2000;
	public Weapons(Handler handler) {
		this.handler=handler;
		primary= Sword.mirror;
		secondary = Equipment.shield;
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
			primaryCooldown=800;
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
