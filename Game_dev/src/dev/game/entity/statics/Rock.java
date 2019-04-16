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
	super(handler,x,y,(int)(1.5*Tile.TILEWIDTH/1.2), (int) (1.5*Tile.TILEHEIGHT));
	this.health=1;
	name="Rock";
	bounds.x=10;
	bounds.y=(int)(height/1.5f);
	bounds.width = width-20;
	bounds.height=(int)(height-height/1.5f);
	}
	
	public void die() {
		//add random location variability
		health=10;
		active=true;
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
}
