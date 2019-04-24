package dev.game.worlds;

import java.awt.Graphics;
import java.io.IOException;

import dev.game.Handler;
import dev.game.creatures.Boss;
import dev.game.creatures.BossHandLeft;
import dev.game.creatures.BossHandRight;
import dev.game.creatures.Bull;
import dev.game.creatures.Frienemy;
import dev.game.creatures.Player;
import dev.game.creatures.Player2;
import dev.game.creatures.PlayerMirror;
import dev.game.creatures.Sheep;
import dev.game.creatures.Wizard;
import dev.game.entity.EntityManager;
import dev.game.entity.projectile.Arrow;
import dev.game.entity.projectile.Projectile;
import dev.game.entity.statics.Rock;
import dev.game.entity.statics.TransitionSpace;
import dev.game.entity.statics.Tree;
import dev.game.item.ItemManager;
import dev.game.tile.Tile;
import dev.game.utils.Utils;

public class World {
	protected Handler handler;
	private int width, height;
	private int entityNum;
	private int spawnX,spawnY;
	protected EntityManager entityManager,projectileManager;
	protected ItemManager itemManager;
	private int[][] tiles;
	public enum Direction{UP,DOWN,LEFT,RIGHT};
	protected String pathWorldTemp;
	protected String pathEntityTemp;
	protected boolean flagToLoad=true;
	protected boolean ticking=false;
	private TransitionSpace world2,world1;
	private String currentWorldPath="Resources/worlds/world1.txt"
			,currentEntityPath="Resources/entities/world1.txt";
	private int deathCounter=0;
	public enum WorldType{ARENA,NORMAL,MIRROR};
	private WorldType worldType=WorldType.NORMAL;
	boolean trigger1=true,trigger2=true;
	//constructor
	public World(Handler handler, String worldPath,String entityPath,WorldType arena) {
		this.handler = handler;
		this.worldType=arena;
		projectileManager=new EntityManager(handler,new Sheep(handler,0,0,0,0));
		entityManager=new EntityManager(handler,new Player(handler,0,0,0,0));		
		itemManager= new ItemManager(handler);
		initSaveData();
		loadNewWorld(worldPath,entityPath);		
	}

	private void initSaveData() {
		// TODO Auto-generated method stub
		flagToLoad=true;
		loadNewWorld("Resources/Reserve_Data/worlds/world1.txt","Resources/Reserve_Data/entities/world1.txt");
		currentWorldPath="Resources/worlds/world1.txt";
		currentEntityPath="Resources/entities/world1.txt";
		saveWorld();
		entityManager.clear1();
		projectileManager.clear2();
		itemManager.clear();

		flagToLoad=true;
		loadNewWorld("Resources/Reserve_Data/worlds/world2.txt","Resources/Reserve_Data/entities/world2.txt");
		currentWorldPath="Resources/worlds/world2.txt";
		currentEntityPath="Resources/entities/world2.txt";
		saveWorld();
		entityManager.clear1();
		projectileManager.clear2();
		itemManager.clear();

		flagToLoad=true;
		loadNewWorld("Resources/Reserve_Data/worlds/world3.txt","Resources/Reserve_Data/entities/world3.txt");
		currentWorldPath="Resources/worlds/world3.txt";
		currentEntityPath="Resources/entities/world3.txt";
		saveWorld();
		entityManager.clear1();
		projectileManager.clear2();
		itemManager.clear();

		flagToLoad=true;
		loadNewWorld("Resources/Reserve_Data/worlds/world4.txt","Resources/Reserve_Data/entities/world4.txt");
		currentWorldPath="Resources/worlds/world4.txt";
		currentEntityPath="Resources/entities/world4.txt";
		saveWorld();
		entityManager.clear1();
		projectileManager.clear2();
		itemManager.clear();		
		
		flagToLoad=true;
		loadNewWorld("Resources/Reserve_Data/worlds/world5.txt","Resources/Reserve_Data/entities/world5.txt");
		currentWorldPath="Resources/worlds/world5.txt";
		currentEntityPath="Resources/entities/world5.txt";
		saveWorld();
		entityManager.clear1();
		projectileManager.clear2();
		itemManager.clear();
		
	}

	private void updateSpawns() {
		//do creature spawning stuff here
		if(deathCounter>20) {
			
		}
		else if(deathCounter>15) {
			
		}
		else if(deathCounter>=10&& trigger2) {
			Sheep sheep= new Sheep(handler,0,0, 100, 100);
			sheep.setX(128);
			sheep.setY(128);
			entityManager.addEntity(sheep);
			Wizard wizard= new Wizard(handler,0,0,100,100,false);
			wizard.setX(300);
			wizard.setY(128);
			entityManager.addEntity(wizard);
			wizard.setX(450);
			wizard.setY(128);
			entityManager.addEntity(wizard);
			Bull bull= new Bull(handler,0,0,100,100,false);
			bull.setX(600);
			bull.setY(128);
			entityManager.addEntity(bull);
			bull= new Bull(handler,0,0,100,100,false);
			bull.setY(128);
			bull.setX(800);
			entityManager.addEntity(bull);
			trigger2=false;
		}
		else if(deathCounter>=5&&trigger1) {
			Sheep sheep= new Sheep(handler,0,0, 100, 100);
			sheep.setX(128);
			sheep.setY(128);
			entityManager.addEntity(sheep);
			Wizard wizard= new Wizard(handler,0,0,100,100,false);
			wizard.setX(300);
			wizard.setY(128);
			entityManager.addEntity(wizard);
			wizard.setX(450);
			wizard.setY(128);
			entityManager.addEntity(wizard);
			Bull bull= new Bull(handler,0,0,100,100,false);
			bull.setX(600);
			bull.setY(128);
			entityManager.addEntity(bull);
			bull= new Bull(handler,0,0,100,100,false);
			bull.setY(128);
			bull.setX(800);
			entityManager.addEntity(bull);
			trigger1=false;
		}
	}
	
	private void deathCounterUpdate() {
		deathCounter=entityManager.getDeathCount();
	}
	public void tick() {
		ticking=true;
		itemManager.tick();
		entityManager.tick();
		projectileManager.tick();
		if (worldType==WorldType.ARENA) {
			deathCounterUpdate();
			updateSpawns();
		}
		ticking=false;
		if(flagToLoad) {
			loadNewWorld(pathWorldTemp,pathEntityTemp);
			pathWorldTemp="";
			pathEntityTemp="";
			flagToLoad=false;
		}
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
		projectileManager.render(g);
	}
	
	public Tile getTile(int x, int y) {
		//check if coords is out of bounds to avoid throwing an error
		if(x < 0 || y < 0 || x >= width || y >= height) {
			//if out of bounds return a grass tile
			return Tile.grassTile;
		}
		//get the specific tile from the tile array
		Tile t = Tile.tiles[tiles[x][y]];
		
		//returns a grass tile if no tile in the tiles array
		if(t==null) {return Tile.grassTile;}
		
		
		return t;
	}
	public void loadNewWorld(String pathWorld,String pathEntity) {
		this.currentWorldPath=pathWorld;
		this.currentEntityPath=pathEntity;
		if (!ticking&&flagToLoad) {
			loadWorld(pathWorld);
			loadEntities(pathEntity);
			entityManager.getPlayer().setX(spawnX);
			entityManager.getPlayer().setY(spawnY);
			if(worldType==WorldType.MIRROR) {
				PlayerMirror mirrorPlayer=new PlayerMirror(handler, spawnX+100, spawnY, 64, 64,entityManager.getPlayer());
				entityManager.addEntity(mirrorPlayer);
			}
			flagToLoad=false;
		}else {
			flagToLoad=true;
			pathWorldTemp=pathWorld;
			pathEntityTemp=pathEntity;
		}
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
	
	public void saveWorld() {
		//save tile data may be useful later
		if(worldType==WorldType.MIRROR) {return;}
		int xSpawn=(int) entityManager.getPlayer().getX();
		int ySpawn=(int) entityManager.getPlayer().getY();
		String data=width+" "+height+"\n"+xSpawn+" "+ySpawn+"\n";
		for (int y=0;y<height;y++) {
			for (int x=0;x<width;x++) {
				data=data+tiles[x][y]+" ";
			}
			data=data+"\n";
		}
		try {
			Utils.saveFileAsString(this.currentWorldPath,data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data=(entityManager.getEntities().size()-1)+"\n";
		for(int y=0;y<entityManager.getEntities().size();y++) {
			if(entityManager.getEntities().get(y)!=entityManager.getPlayer()) {
				data=data+((int)entityManager.getEntities().get(y).getId())+" "+((int)entityManager.getEntities().get(y).getX())+" "+((int)entityManager.getEntities().get(y).getY())+" 0\n";
			}
		}
		try {
			Utils.saveFileAsString(this.currentEntityPath,data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void loadEntities(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		entityNum = Utils.parseInt(tokens[0]);
		int entityType;
		int entitySpawnX;
		int entitySpawnY;
		int entitySpawnDirectionInt;
		Direction entitySpawnDirection=Direction.DOWN;
		for (int y=0;y<entityNum;y++) {
			entityType=Utils.parseInt(tokens[(y*4)+1]);
			entitySpawnX=Utils.parseInt(tokens[(y*4)+2]);
			entitySpawnY=Utils.parseInt(tokens[(y*4)+3]);
			entitySpawnDirectionInt=Utils.parseInt(tokens[(y*4)+3]);
			
			if (entitySpawnDirectionInt==0) {
				entitySpawnDirection=Direction.UP;
			}else if (entitySpawnDirectionInt==1) {
				entitySpawnDirection=Direction.RIGHT;
			}else if (entitySpawnDirectionInt==2) {
				entitySpawnDirection=Direction.DOWN;
			}else if (entitySpawnDirectionInt==3) {
				entitySpawnDirection=Direction.LEFT;}
			
			if (entityType==0) {
				entityManager.addEntity(new Tree(handler,entitySpawnX,entitySpawnY));
			}else if(entityType==1) {
				Sheep sheep= new Sheep(handler,0,0, 100, 100);
				sheep.setX(entitySpawnX);
				sheep.setY(entitySpawnY);
				entityManager.addEntity(sheep);
			}else if(entityType==2) {
				Arrow arrow= new Arrow(handler,0,0);
				arrow.setDirection(entitySpawnDirection);
				arrow.setX(entitySpawnX);
				arrow.setY(entitySpawnY);
				projectileManager.addEntity(arrow);
			}else if(entityType==3) {
				Wizard wizard= new Wizard(handler,0,0,100,100,false);
				wizard.setX(entitySpawnX);
				wizard.setY(entitySpawnY);
				entityManager.addEntity(wizard);
			}else if(entityType==4) {
				Frienemy frienemy= new Frienemy(handler,0,0,100,100);
				frienemy.setX(entitySpawnX);
				frienemy.setY(entitySpawnY);
				entityManager.addEntity(frienemy);

			}else if(entityType==5) {
				entityManager.addEntity(new Rock(handler,entitySpawnX,entitySpawnY));
			}else if(entityType==6) {
				Bull bull= new Bull(handler,0,0,100,100,false);
				bull.setX(entitySpawnX);
				bull.setY(entitySpawnY);
				entityManager.addEntity(bull);
			}else if(entityType==7) {
				BossHandLeft leftHand=new BossHandLeft(handler,300,400, 50, 50);
				BossHandRight rightHand=new BossHandRight(handler,400,400, 50, 50);
				entityManager.addEntity(rightHand);
				entityManager.addEntity(leftHand);
				Boss boss= new Boss(handler,0,0,200,150,leftHand,rightHand);
				boss.setX(entitySpawnX);
				boss.setY(entitySpawnY);
				entityManager.addEntity(boss);
				entityManager.setBoss(boss);
				
			}
			else if(entityType==95) {
				TransitionSpace world5= new TransitionSpace(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world5.txt","Resources/entities/world5.txt",WorldType.NORMAL);
				world5.setX(entitySpawnX);
				world5.setY(entitySpawnY);
				entityManager.addEntity(world5);
			}
			else if(entityType==94) {
				TransitionSpace world4= new TransitionSpace(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world4.txt","Resources/entities/world4.txt",WorldType.MIRROR);
				world4.setX(entitySpawnX);
				world4.setY(entitySpawnY);
				entityManager.addEntity(world4);
			}
			else if(entityType==93) {
				TransitionSpace world3= new TransitionSpace(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world3.txt","Resources/entities/world3.txt",WorldType.ARENA);
				world3.setX(entitySpawnX);
				world3.setY(entitySpawnY);
				entityManager.addEntity(world3);
			}
			else if(entityType==92) {
				world2= new TransitionSpace(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world2.txt","Resources/entities/world2.txt",WorldType.NORMAL);
				world2.setX(entitySpawnX);
				world2.setY(entitySpawnY);
				entityManager.addEntity(world2);
			}
			else if(entityType==91) {
				world1= new TransitionSpace(handler,entitySpawnX,entitySpawnY,32,48,entityType,"Resources/worlds/world1.txt","Resources/entities/world1.txt",WorldType.NORMAL);
				world1.setX(entitySpawnX);
				world1.setY(entitySpawnY);
				entityManager.addEntity(world1);
			}
		}
	}
	//getters and setters
	
	public int getWidth() {
		return width;
	}
	public EntityManager getProjectileManager() {
		return projectileManager;
	}

	public void setProjectileManager(EntityManager projectileManager) {
		this.projectileManager = projectileManager;
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

	public WorldType getWorldType() {
		return worldType;
	}

	public void setWorldType(WorldType worldType) {
		this.worldType = worldType;
	}	
}
