package dev.game.tile;

import java.awt.image.BufferedImage;

import dev.launcher.Assets;

public class WallLeft extends Tile{

	public WallLeft(int id) {
		super(Assets.wall_left, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
