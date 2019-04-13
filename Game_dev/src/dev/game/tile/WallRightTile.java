package dev.game.tile;


import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class WallRightTile extends Tile{

	public WallRightTile(int id) {
		super(Assets.wall_right, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}
}


