package dev.game;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.ImageLoader.loader;
import dev.display.*;
import dev.launcher.Assets;
import dev.launcher.SpriteSheet;
public class game implements Runnable{
	private display Display;
	private Thread thread;
	public String title;
	public int width, height;
	private BufferStrategy bs;
	private Graphics g;
	private boolean running;
	
	private BufferedImage testImage;
	private SpriteSheet sheet;
	
	public game(String title, int width, int height) {
		this.width = width;
		this.height=height;
		this.title=title;
		
		
	}
	private void init() {
		Display = new display(title,width,height);
		testImage = loader.loadImage("/Textures/K13.png");
		sheet=new SpriteSheet(testImage);
	}
	
	public synchronized void start() {
		if (running) {return;}
		running=true;
		thread = new Thread(this) ;
		thread.start();
		}
	
	public synchronized void stop() {
		if(!running) {return;}
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {

		init();
		while (running) {
			tick();
			render();
		}
	}
	private void tick() {
		
	}
	private void render(){
		bs = Display.getCanvas().getBufferStrategy();
		if(bs == null) {
			Display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width,height);
		
		//g.drawImage(testImage,0,0,null);
		
		//int x=0,y=0;
		//int width=1024;
		//int height=768;
		//g.drawImage(sheet.crop(x, y, width, height), 0, 0, null);
		
		//g.drawImage(Assets.sprite1,0,0,null);
		
		bs.show();
		g.dispose();
	}
}
