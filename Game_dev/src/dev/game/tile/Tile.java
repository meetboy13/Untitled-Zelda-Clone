package dev.game.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class Tile {
	
	//if need more tiles increase 256 to more than 256
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new NonSolidTile(Assets.grass,0);
	public static Tile waterTile = new SolidTile(Assets.water,1);
	public static Tile dirtTile = new NonSolidTile(Assets.dirt,2);
	public static Tile wall_left = new SolidTile(Assets.wall_left,3);
	public static Tile wall_right = new SolidTile(Assets.wall_right,4);
	public static Tile wall_down = new SolidTile(Assets.wall_down,5);
	public static Tile wall_up = new SolidTile(Assets.wall_up,6);
	public static Tile wall_right_down = new SolidTile(Assets.wall_right_down,7);
	public static Tile wall_left_down = new SolidTile(Assets.wall_left_down,8);
	public static Tile wall_right_up = new SolidTile(Assets.wall_right_up,9);
	public static Tile wall_left_up = new SolidTile(Assets.wall_left_up,10);
	public static Tile stone = new NonSolidTile(Assets.stone,11);
	public static Tile stone_wall_left = new SolidTile(Assets.stone_wall_left,12);
	public static Tile stone_wall_right = new SolidTile(Assets.stone_wall_right,13);
	public static Tile stone_wall_down = new SolidTile(Assets.stone_wall_down,14);
	public static Tile stone_wall_up = new SolidTile(Assets.stone_wall_up,15);
	public static Tile stone_wall_right_down = new SolidTile(Assets.stone_wall_right_down,16);
	public static Tile stone_wall_left_down = new SolidTile(Assets.stone_wall_left_down,17);
	public static Tile stone_wall_right_up = new SolidTile(Assets.stone_wall_right_up,18);
	public static Tile stone_wall_left_up = new SolidTile(Assets.stone_wall_left_up,19);
	public static Tile bridge_left = new NonSolidTile(Assets.bridge_left,20);
	public static Tile bridge_right = new NonSolidTile(Assets.bridge_right,21);
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
