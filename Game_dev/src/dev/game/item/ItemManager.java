package dev.game.item;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.game.Handler;

public class ItemManager {
	private Handler handler;
	private ArrayList<Item> items;
	private boolean ticking=true,flagToClear=false;
	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	public void tick() {
		//set a boolean to true to check later to see if its safe to mess with the array
		ticking=true;
		Iterator<Item> it =items.iterator();
		while(it.hasNext()) {
			Item i=it.next();
			i.tick();
			if(i.isPickedUp()) {
				it.remove();
			}
		}
		ticking=false;
		if(flagToClear) {
			clear();
			flagToClear=false;
		}
	}
	public void render(Graphics g) {
		for(Item i : items) {
			i.render(g);
		}
	}
	public void addItem(Item i) {
		i.setHandler(handler);
		items.add(i);
	}
	public void clear() {
		if (!ticking) {
			items=new ArrayList<Item>();
		}else {
			flagToClear=true;
		}
	}
	
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
}
