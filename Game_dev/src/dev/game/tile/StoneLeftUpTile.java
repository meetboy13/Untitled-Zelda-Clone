package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class StoneLeftUpTile extends Tile {
	public StoneLeftUpTile( int id) {
		super(Assets.stone_wall_left_up, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
