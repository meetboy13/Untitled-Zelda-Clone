package dev.game.entity.statics;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.item.Item;
import dev.game.tile.Tile;
import dev.launcher.Assets;

public class Rock extends StaticEntity{
	public Rock(Handler handler,float x, float y) {
	super(handler,x,y,(int)(1.5*64/1.2), (int) (1.5*64));
	this.health=1;
	id=5;
	name="Rock";
	bounds.x=20;
	bounds.y=(int)(height/2);
	bounds.width = width-40;
	bounds.height=(int)(height-height/1.25f);
	}
	
	public void die() {
		//add random location variability
		health=10;
		active=true;
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		
		//g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
}
