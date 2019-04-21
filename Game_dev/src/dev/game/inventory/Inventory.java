package dev.game.inventory;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.game.Handler;
import dev.game.item.Item;

public class Inventory {
	
	
	public enum Equipment{shield,spear,wand,none};
	private Equipment secondary = Equipment.shield;

	public enum Sword{training,mirror,OP};
	private Sword primary = Sword.mirror;
	
	private Handler handler;
	private boolean active = false;
	private ArrayList<Item> inventoryItems;
	public Inventory(Handler handler){
		this.handler=handler;
		inventoryItems=new ArrayList<Item>();
	}
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			active=!active;
		}
		if(!active) {return;}
		
		for(Item i: inventoryItems) {
			System.out.println(i.getName()+"      "+i.getCount());
		}
		
	}
	public void render(Graphics g) {
		if(!active) {return;}
		
	}
	
	public void addItem(Item item) {
		for(Item i: inventoryItems) {
			if(i.getId()==item.getId()) {
				i.setCount(i.getCount()+1);
				return;
			}
		}
		inventoryItems.add(item);
	}
	public int getItemCount(int id) {
		for(Item i: inventoryItems) {
			if(i.getId()==id) {
				return i.getCount();

			}
		}
		return 0;
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
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
	}
	
}
