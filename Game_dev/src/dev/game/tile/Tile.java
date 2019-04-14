package dev.game.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	//if need more tiles increase 256 to more than 256
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile rockTile = new RockTile(1);
	public static Tile dirtTile = new DirtTile(2);
	public static Tile wall_left = new WallLeft(3);
	public static Tile wall_right = new WallRightTile(4);
	public static Tile wall_down = new WallDown(5);
	public static Tile wall_up = new WallUpTile(6);
	public static Tile wall_right_down = new WallRightDownTile(7);
	public static Tile wall_left_down = new WallLeftDownTile(8);
	
	//class
	protected BufferedImage texture;
	protected final int id;
	public final static int TILEWIDTH=64;
	public final static int TILEHEIGHT=64;
	
	
	public Tile(BufferedImage texture, int id) {
		this.texture=texture;
		this.id=id;
		
		tiles[id]=this;
	}
	public void tick() {
		
	}
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	public boolean isSolid() {
		//returns true if impassible
		return false;
	}
	public int getId() {
		return id;
	}
}
