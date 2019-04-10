package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.entity.Entity;

public abstract class StaticEntity extends Entity{

	public StaticEntity(Handler handler,float x ,float y, int width, int height) {
		super(handler,x,y,width,height);
	}
}
