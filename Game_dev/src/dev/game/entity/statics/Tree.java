package dev.game.entity.statics;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.item.Item;
import dev.game.tile.Tile;
import dev.launcher.Assets;

public class Tree extends StaticEntity{
	public Tree(Handler handler,float x, float y) {
	super(handler,x,y,Tile.TILEWIDTH/2,Tile.TILEHEIGHT);
	
	bounds.x=10;
	bounds.y=(int)(height/1.5f);
	bounds.width = width-20;
	bounds.height=(int)(height-height/1.5f);
	}
	public void die() {
		//add random location variability
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x,(int) y));
	}
	@Override
	public void tick() {
		
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
	}
}
