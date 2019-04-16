package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class StoneLeftDownTile extends Tile {

	public StoneLeftDownTile( int id) {
		super(Assets.stone_wall_left_down, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}
}
