package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class WallRightDownTile extends Tile {

	public WallRightDownTile(int id) {
		super(Assets.wall_right_down, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
