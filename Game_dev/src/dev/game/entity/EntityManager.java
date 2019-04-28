package dev.game.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.game.Handler;
import dev.game.creatures.Player;

public class EntityManager {
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> entitiesToAdd;
	private int deathCount=0;
	private boolean ticking=false,flagToAdd=false;
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
		entitiesToAdd=new ArrayList<Entity>();
		addEntity(player);
	}
	//constructor for blank array
	public EntityManager(Handler handler) {
		this.handler = handler;
		entities=new ArrayList<Entity>();
	}
	
	public void tick() {
		//set a boolean to true to prevent changes in the middle of a tick
		ticking=true;
		Iterator<Entity> it = entities.iterator();
		//tick all entities
		while(it.hasNext()) {
			Entity e=it.next();
			e.tick();
			if(!e.isActive()) {
				//remove non active entities
				it.remove();
				deathCount++;
			}
		}
		//sort the entities in terms of their y
		entities.sort(renderOrder);
		ticking=false;
		if(flagToAdd) {

		}
	}
	public void render(Graphics g) {
		//render all entities
		for(Entity e : entities) {
			e.render(g);
		}
	}
	
	//add entities to array
	public void addEntity(Entity e) {
		if(!ticking && flagToAdd){
			//if safe to do so add entities
			Iterator<Entity> it = entitiesToAdd.iterator();
			while(it.hasNext()) {
				Entity ent=it.next();
				entities.add(ent);
				it.remove();
			}
			flagToAdd=false;
		}else if (!ticking){
			entities.add(e);
		}else {
			//add to an array to add later
			flagToAdd=true;
			entitiesToAdd.add(e);
		}
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
