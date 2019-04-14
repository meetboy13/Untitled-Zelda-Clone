package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class WallLeftDownTile extends Tile {

	public WallLeftDownTile(int id) {
		super(Assets.wall_left_down, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
