package dev.game.entity.statics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.item.Item;
import dev.game.tile.Tile;
import dev.launcher.Assets;

public class Tree extends StaticEntity{

	private long lastAttackTimer,attackCooldown=500,attackTimer=attackCooldown;
	private Random rand = new Random();

	public Tree(Handler handler,float x, float y) {
	super(handler,x,y,(int)(Tile.TILEWIDTH/.6),Tile.TILEHEIGHT*2);
	this.health=1;
	name="tree";
	bounds.x=10;
	bounds.y=(int)(height/1.5f);
	bounds.width = width-20;
	bounds.height=(int)(height-height/1.5f);
	}
	
	
	private void checkAttacks() {
		attackTimer+=System.currentTimeMillis()-lastAttackTimer;
		lastAttackTimer=System.currentTimeMillis();
		if(attackTimer<attackCooldown) {
			return;
		}
		// TODO Auto-generated method stub
		Rectangle cb =getCollisionBounds(0,0);
		Rectangle ar= new Rectangle();
		int arSize=20;
		ar.width=arSize*2+bounds.width;
		ar.height=arSize*2+bounds.height;
		ar.y=cb.y-arSize;
		ar.x=cb.x-arSize;
		attackTimer=0;
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {continue;}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
				int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
				e.hurt(1,deltaX,deltaY);
			}
		}
	}
	
	
	
	
	public void die() {
		//add random location variability
		int xVar=rand.nextInt(128)-64;
		int yVar=rand.nextInt(128)-64;
		handler.getWorld().getItemManager().addItem(Item.drop.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));
	}
	@Override
	public void tick() {
		checkAttacks();
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
	}
}
