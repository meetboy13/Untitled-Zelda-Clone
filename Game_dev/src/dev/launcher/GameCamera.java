package dev.launcher;

import dev.game.Handler;
import dev.game.game;
import dev.game.entity.Entity;
import dev.game.tile.Tile;

public class GameCamera {
	private float xOffset, yOffset;
	private Handler handler;
	public GameCamera(Handler handler,float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.handler=handler;
	}
	
	public void checkBlankSpace() {
		if(xOffset<0) {
			xOffset=0;
		}
		if(xOffset>handler.getWorld().getWidth()*Tile.TILEWIDTH-handler.getWidth()) {
			xOffset=handler.getWorld().getWidth()*Tile.TILEWIDTH-handler.getWidth();
		}
			
		if(yOffset<0) {
			yOffset=0;
		}

		if(yOffset>handler.getWorld().getHeight()*Tile.TILEHEIGHT-handler.getHeight()) {
			yOffset=handler.getWorld().getHeight()*Tile.TILEHEIGHT-handler.getHeight();
		}
	}
	
	public void centeronEntity(Entity e) {
		xOffset = e.getX()-handler.getWidth()/2+e.getWidth()/2;
		yOffset = e.getY()-handler.getHeight()/2+e.getHeight()/2;
		checkBlankSpace();
	}
	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}
	
	public float getxOffset() {
		return xOffset;
	}
	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
		checkBlankSpace();
	}
	public float getyOffset() {
		return yOffset;
	}
	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
		checkBlankSpace();
	}
}
