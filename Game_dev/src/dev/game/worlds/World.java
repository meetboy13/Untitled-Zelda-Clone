package dev.game.worlds;

import java.awt.Graphics;

import dev.game.game;
import dev.game.tile.Tile;
import dev.game.utils.Utils;

public class World {
	private game Game;
	private int width, height;
	private int spawnX,spawnY;
	private int[][] tiles;
	
	public World(game Game, String path) {
		this.Game=Game;
		loadWorld(path);
	}
	public void tick() {
		
		
	}
	
	public void render (Graphics g) {
		int xStart=(int)Math.max(0, Game.getGameCamera().getxOffset()/Tile.TILEWIDTH);
		int xEnd=(int)Math.min(width, (Game.getGameCamera().getxOffset()+Game.getWidth())/Tile.TILEWIDTH + 1);
		int yStart=(int)Math.max(0, Game.getGameCamera().getyOffset()/Tile.TILEHEIGHT);
		int yEnd=(int)Math.min(height, (Game.getGameCamera().getyOffset()+Game.getHeight())/Tile.TILEHEIGHT+1);
		for (int y=yStart;y<yEnd;y++) {
			for (int x=xStart;x<xEnd;x++) {
				getTile(x,y).render(g, (int)(x * Tile.TILEWIDTH-Game.getGameCamera().getxOffset()),(int)( y*Tile.TILEHEIGHT-Game.getGameCamera().getyOffset()));
			}
		}
	}
	
	public Tile getTile(int x, int y) {
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
}
