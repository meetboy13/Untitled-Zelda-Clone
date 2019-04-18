package dev.game.tile;

import java.awt.image.BufferedImage;

public class SolidTile extends Tile {

	public SolidTile(BufferedImage texture, int id) {
		super(texture, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid() {
		//returns true if impassible
		return true;
	}

}
