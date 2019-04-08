package dev.game.entity;

import java.awt.Graphics;

import dev.game.game;

public abstract class Entity {
	//entity coordinates
	protected float x,y;
	
	//entities width and height
	protected int width,height;
	
	protected game Game;
	//getters and setters for all variables
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Entity(game Game,  float x, float y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.Game=Game;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
}
