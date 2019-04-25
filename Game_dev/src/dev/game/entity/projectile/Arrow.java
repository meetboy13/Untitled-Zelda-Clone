package dev.game.entity.projectile;

import java.awt.Graphics;
import java.util.Random;

import dev.game.Handler;
import dev.game.item.Item;
import dev.game.worlds.World.Direction;
import dev.launcher.Assets;

public class Arrow extends Projectile{
	private Random rand = new Random();

	public Arrow(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_PROJECTILE_WIDTH, DEFAULT_PROJECTILE_HEIGHT);
		// TODO Auto-generated constructor stub
		id=2;
		bounds.x+=23;
		bounds.y+=5;
		bounds.height-=10;
		bounds.width-=23*2;
		texture=Assets.spear[0];
	}
	@Override
	public void setDirection(Direction direction) {
		if(direction==Direction.UP) {
			yMove=-speed;
			texture=Assets.spear[1];
		}
		else if(direction==Direction.DOWN) {
			yMove=speed;
			texture=Assets.spear[3];
		}
		else if(direction==Direction.LEFT) {
			xMove=-speed;
			texture=Assets.spear[2];
			this.getBounds().y=23;
			this.getBounds().x=0;
			this.getBounds().width=DEFAULT_PROJECTILE_WIDTH-10;
			this.getBounds().height=DEFAULT_PROJECTILE_HEIGHT-23*2;
		}
		else if(direction==Direction.RIGHT) {
			xMove=speed;
			texture=Assets.spear[0];
			this.getBounds().y=20;
			this.getBounds().x=10;
			this.getBounds().width=DEFAULT_PROJECTILE_WIDTH-10;
			this.getBounds().height=DEFAULT_PROJECTILE_HEIGHT-23*2;
		}
	}
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(texture,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),DEFAULT_PROJECTILE_WIDTH,DEFAULT_PROJECTILE_HEIGHT,null);
		g.drawRect((int)(this.getCollisionBounds(0, 0).x-handler.getGameCamera().getxOffset()),(int)(this.getCollisionBounds(0, 0).y-handler.getGameCamera().getyOffset()), this.getCollisionBounds(0, 0).width, this.getCollisionBounds(0, 0).height);
	}
	@Override
	public void die() {
		// TODO Auto-generated method stub
		this.active=false;
		int xVar=(int) (rand.nextInt((int) this.getBounds().getWidth())-this.getBounds().getWidth()/2);
		int yVar=(int) (rand.nextInt((int) this.getBounds().getHeight())-this.getBounds().getHeight()/2);
		handler.getWorld().getItemManager().addItem(Item.javelin.createNew((int)x+this.width/2+xVar,(int) y+this.height/2+yVar));
	}
}
