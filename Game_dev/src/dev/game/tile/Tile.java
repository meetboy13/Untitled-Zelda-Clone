package dev.game.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class Tile {
	
	//if need more tiles increase 256 to more than 256
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile waterTile = new WaterTile(1);
	public static Tile dirtTile = new DirtTile(2);
	public static Tile wall_left = new WallLeft(3);
	public static Tile wall_right = new WallRightTile(4);
	public static Tile wall_down = new WallDown(5);
	public static Tile wall_up = new WallUpTile(6);
	public static Tile wall_right_down = new WallRightDownTile(7);
	public static Tile wall_left_down = new WallLeftDownTile(8);
	public static Tile wall_right_up = new WallRightUpTile(9);
	public static Tile wall_left_up = new WallLeftUpTile(10);
	public static Tile stone = new StoneTile(11);
	public static Tile stone_wall_left = new StoneLeftTile(12);
	public static Tile stone_wall_right = new StoneRightTile(13);
	public static Tile stone_wall_down = new StoneDownTile(14);
	public static Tile stone_wall_up = new StoneUpTile(15);
	public static Tile stone_wall_right_down = new StoneRightDownTile(16);
	public static Tile stone_wall_left_down = new StoneLeftDownTile(17);
	public static Tile stone_wall_right_up = new StoneRightUpTile(18);
	public static Tile stone_wall_left_up = new StoneLeftUpTile(19);
	public static Tile bridge_left = new BridgeLeftTile(20);
	public static Tile bridge_right = new BridgeRightTile(21);
	public static Tile stone_corner_left_up = new SolidTile(Assets.stone_wall_corner_left_up,22);
	public static Tile stone_corner_right_up = new SolidTile(Assets.stone_wall_corner_right_up,23);
	public static Tile stone_corner_left_down = new SolidTile(Assets.stone_wall_corner_left_down,24);
	public static Tile stone_corner_right_down = new SolidTile(Assets.stone_wall_corner_right_down,25);
	public static Tile stone_null = new SolidTile(Assets.stone_null,26);
	
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
