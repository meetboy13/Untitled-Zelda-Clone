package dev.game.item;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.game.Handler;
import dev.launcher.Assets;

public class Item {
	
	public static Item[] items = new Item[256];
	public static Item drop = new Item(Assets.drop,"Gem",0);
	public static Item key = new Item(Assets.drop,"Key",1);
	
	public static final int ITEMWIDTH=20,ITEMHEIGHT=32;
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	protected int x,y,count;
	protected Rectangle bounds;
	protected boolean pickedUp=false;
	protected int width, height;
	public Item(BufferedImage texture, String name, int id) {
		this.texture=texture;
		this.name=name;
		this.id=id;
		width=ITEMWIDTH;
		height=ITEMHEIGHT;
		count=1;
		bounds=new Rectangle(x,y,width,height);
		items[id]=this;
	}
	public void  tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp=true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}
	
	public void render(Graphics g) {
		if (handler==null) {return;}
		render(g,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()));
	}
	public void render(Graphics g,int x, int y) {
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}
	public void setPosition(int x, int y) {
		this.x=x;
		this.y=y;
		bounds.x=x;
		bounds.y=y;
	}
	public Item createNew(int x,int y) {
		Item i = new Item(texture,name,id);
		i.setPosition(x, y);
		return i;
	}
	//getters and setters
	
	public Handler getHandler() {
		return handler;
	}
	public boolean isPickedUp() {
		return pickedUp;
	}
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		bounds.x=x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		bounds.y=y;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void remove() {
		// TODO Auto-generated method stub
		pickedUp=true;
	}
	
}
