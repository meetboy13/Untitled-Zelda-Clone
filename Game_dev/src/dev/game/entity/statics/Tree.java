package dev.game.entity.statics;

import java.awt.Graphics;
import java.util.Random;

import dev.game.Handler;
import dev.game.item.Item;
import dev.game.tile.Tile;
import dev.launcher.Assets;

public class Tree extends StaticEntity{

	private Random rand = new Random();
	//constructor
	public Tree(Handler handler,float x, float y) {
	super(handler,x,y,(int)(Tile.TILEWIDTH/.6),Tile.TILEHEIGHT*2);
	this.health=1;
	name="tree";
	bounds.x=10;
	bounds.y=(int)(height/1.5f);
	bounds.width = width-20;
	bounds.height=(int)(height-height/1.5f);
	}
	
	public void die() {
		//add random location variability
		int xVar=(int) (rand.nextInt((int) this.getBounds().getWidth())-this.getBounds().getWidth()/2);
		int yVar=(int) (rand.nextInt((int) this.getBounds().getHeight())-this.getBounds().getHeight()/2);
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
	}
}
