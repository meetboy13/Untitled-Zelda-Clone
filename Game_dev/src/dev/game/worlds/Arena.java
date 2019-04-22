package dev.game.worlds;

import dev.game.Handler;
import dev.game.creatures.Player;
import dev.game.creatures.Sheep;
import dev.game.entity.EntityManager;
import dev.game.item.ItemManager;

public class Arena extends World {
	private int deathCounter=0;
	public Arena(Handler handler, String worldPath, String entityPath) {
		super(handler, worldPath, entityPath);
		// TODO Auto-generated constructor stub
		this.handler = handler;
		projectileManager=new EntityManager(handler,new Sheep(handler,0,0,0,0));
		entityManager=new EntityManager(handler,new Player(handler,0,0,0,0));		
		itemManager= new ItemManager(handler);
		loadNewWorld(worldPath,entityPath);	
		pathWorldTemp="";
		pathEntityTemp="";
		
	}
	@Override
	public void tick() {
		ticking=true;
		itemManager.tick();
		entityManager.tick();
		projectileManager.tick();
		ticking=false;
		if(flagToLoad) {
			loadNewWorld(pathWorldTemp,pathEntityTemp);
			pathWorldTemp="";
			pathEntityTemp="";
			flagToLoad=false;
		}
		deathCounterUpdate();
		updateSpawns();
	}
	private void updateSpawns() {
		//do creature spawning stuff here
	}
	private void deathCounterUpdate() {
		deathCounter=entityManager.getDeathCount();
	}
}
