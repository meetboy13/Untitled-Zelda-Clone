package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class StoneUpTile extends Tile {

	public StoneUpTile( int id) {
		super(Assets.stone_wall_up, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}
}
