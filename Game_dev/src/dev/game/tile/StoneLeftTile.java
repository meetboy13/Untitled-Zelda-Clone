package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class StoneLeftTile extends Tile {

	public StoneLeftTile(int id) {
		super(Assets.stone_wall_left, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
