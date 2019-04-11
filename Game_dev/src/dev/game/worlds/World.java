package dev.game.worlds;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.creatures.Player;
import dev.game.creatures.Sheep;
import dev.game.entity.EntityManager;
import dev.game.entity.projectile.Arrow;
import dev.game.entity.projectile.Projectile;
import dev.game.entity.statics.Tree;
import dev.game.item.ItemManager;
import dev.game.tile.Tile;
import dev.game.utils.Utils;

public class World {
	private Handler handler;
	private int width, height;
	private int spawnX,spawnY;
	private EntityManager entityManager;
	private ItemManager itemManager;
	private int[][] tiles;
	public enum Direction{UP,DOWN,LEFT,RIGHT};
	//constructor
	public World(Handler handler, String path) {
		this.handler = handler;
		entityManager=new EntityManager(handler,new Player(handler,0,0,0,0));
		entityManager.addEntity(new Tree(handler,100,250));
		entityManager.addEntity(new Tree(handler,300,250));
		entityManager.addEntity(new Tree(handler,500,250));
		Sheep sheep= new Sheep(handler,0,0, 100, 100);
		sheep.setX(3*Tile.TILEWIDTH);
		sheep.setY(10*Tile.TILEHEIGHT);
		entityManager.addEntity(sheep);
		itemManager= new ItemManager(handler);
		loadWorld(path);
		//set player to the spawn point
		entityManager.getPlayer().setX(spawnX*Tile.TILEWIDTH);
		entityManager.getPlayer().setY(spawnY*Tile.TILEHEIGHT);
		Arrow sheep1= new Arrow(handler,0,0,100,100);
		sheep1.setDirection(Direction.DOWN);
		sheep1.setX(3*Tile.TILEWIDTH);
		sheep1.setY(5*Tile.TILEHEIGHT);
		entityManager.addEntity(sheep1);
	}

	public void tick() {
		itemManager.tick();
		entityManager.tick();
	}
	
	public void render (Graphics g) {
		//only render what is currently shown on screen
		int xStart=(int)Math.max(0, handler.getGameCamera().getxOffset()/Tile.TILEWIDTH);
		int xEnd=(int)Math.min(width, (handler.getGameCamera().getxOffset()+handler.getWidth())/Tile.TILEWIDTH + 1);
		int yStart=(int)Math.max(0, handler.getGameCamera().getyOffset()/Tile.TILEHEIGHT);
		int yEnd=(int)Math.min(height, (handler.getGameCamera().getyOffset()+handler.getHeight())/Tile.TILEHEIGHT + 1);
		for (int y=yStart;y<yEnd;y++) {
			for (int x=xStart;x<xEnd;x++) {
				getTile(x,y).render(g, (int)(x*Tile.TILEWIDTH-handler.getGameCamera().getxOffset()),(int)(y*Tile.TILEHEIGHT-handler.getGameCamera().getyOffset()));
			}
		}
		itemManager.render(g);
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y) {
		//check if coords is out of bounds
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.grassTile;
		}
		Tile t = Tile.tiles[tiles[x][y]];
		
		//should return an error tile later
		if(t==null) {return Tile.grassTile;}
		
		
		return t;
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		tiles = new int[width][height];

		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x+y*width)+4]);
			}
		}
	}
	//getters and setters
	
	public int getWidth() {
		return width;
	}
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public int getHeight() {
		return height;
	}
	public int getSpawnX() {
		return spawnX;
	}
	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}
	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
