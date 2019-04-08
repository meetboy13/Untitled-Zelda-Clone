package dev.launcher;

import dev.game.game;
import dev.game.entity.Entity;

public class GameCamera {
	private float xOffset, yOffset;
	private game Game;
	public GameCamera(game Game,float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.Game=Game;
	}
	public void centeronEntity(Entity e) {
		xOffset = e.getX()-Game.getWidth()/2+e.getWidth()/2;
		yOffset = e.getY()-Game.getHeight()/2+e.getHeight()/2;
	}
	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
	}
	
	public float getxOffset() {
		return xOffset;
	}
	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}
	public float getyOffset() {
		return yOffset;
	}
	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}
