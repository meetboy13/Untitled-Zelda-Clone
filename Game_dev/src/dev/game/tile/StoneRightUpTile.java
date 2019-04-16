package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class StoneRightUpTile extends Tile {

	public StoneRightUpTile( int id) {
		super(Assets.stone_wall_right_up, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
