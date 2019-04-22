package dev.game.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.game.Handler;
import dev.game.creatures.Player;
import dev.game.creatures.Sheep;

public class EntityManager {
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private int deathCount=0;
	private Comparator<Entity> renderOrder= new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if((a.getY()+a.getHeight())<(b.getY()+b.getHeight())) {
				return -1;
			}
			return 1;
		}
	};
	//constructor
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities=new ArrayList<Entity>();
		addEntity(player);
	}
	public EntityManager(Handler handler, Sheep sheep) {
		this.handler = handler;
		entities=new ArrayList<Entity>();
		sheep.setX(-1000);
		sheep.setY(-1000);
		addEntity(sheep);
	}
	public void tick() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e=it.next();
			e.tick();
			if(!e.isActive()) {
				it.remove();
				deathCount++;
			}
		}
		entities.sort(renderOrder);
	}
	public void render(Graphics g) {
		for(Entity e : entities) {
			e.render(g);
		}
	}
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	
	//getters and setters
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	public void clear1() {
		deathCount=0;
		ArrayList<Entity> temp=new ArrayList<Entity>();
		temp.add(player);
		setEntities(temp);
	}
	public void clear2() {
		ArrayList<Entity> temp=new ArrayList<Entity>();
		setEntities(temp);
	}
	public int getDeathCount() {
		return deathCount;
	}
	
	
}
