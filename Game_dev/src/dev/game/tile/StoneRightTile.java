package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class StoneRightTile extends Tile {

	public StoneRightTile(int id) {
		super(Assets.stone_wall_right, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
