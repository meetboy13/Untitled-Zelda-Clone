package dev.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.ImageLoader.loader;
import dev.display.*;
import dev.game.input.Keymanager;
import dev.game.states.GameState;
import dev.game.states.MenuState;
import dev.game.states.State;
import dev.launcher.Assets;
import dev.launcher.GameCamera;
import dev.launcher.SpriteSheet;

public class game implements Runnable{
	private display Display;
	private Thread thread;
	public String title;
	private int width, height;
	private BufferStrategy bs;
	private Graphics g;
	private boolean running;
	private State gameState;
	private State menuState;
	private Keymanager KeyManager;
	private BufferedImage testImage;
	private SpriteSheet sheet;
	
	private GameCamera gameCamera;
	
	private Handler handler;
	
	//Game constructor
	public game(String title, int width, int height) {
		this.width = width;
		this.height=height;
		this.title=title;
		KeyManager = new Keymanager();
	}
	
	//initialisation function
	private void init() {
		Display = new display(title,width,height);
		Display.getFrame().addKeyListener(KeyManager);
		
		//call the asset initialisation function
		Assets.init();
		
		gameCamera=new GameCamera(this,0,0);
		handler = new Handler(this);
		//need one for each state
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		
		//set initial gamestate
		State.setState(gameState);
	}
	
	//Thread start and stop functions
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
	//getter functions
	public Keymanager getKeyManager() {
		return KeyManager;
	}
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	
	//main run function
	public void run() {
		//game init
		init();
		
		int fps =60;
		double timePerTick = 1000000000/fps;
		double delta =0;
		long now;
		long lastTime = System.nanoTime();
		long timer =0;
		int tick =0;
		
		while (running) {
			//calculates the fps and outputs to terminal
			now = System.nanoTime();
			delta += (now-lastTime)/timePerTick;
			timer += now-lastTime;
			lastTime=now;
			if (delta >=1){
				tick();
				render();
				delta--;
				tick++;
			}
			if (timer >= 1000000000){
				System.out.println("Ticks and Frames: " + tick);
				tick=0;
				timer=0;
			}
		}
	}
	
	//tick for game which calls the state tick 
	//as long as we are in a state
	private void tick() {
		KeyManager.tick();
		if(State.getState() != null) {
			State.getState().tick();
		}
	}
	
	//render method, handles all graphics side stuff
	private void render(){
		bs = Display.getCanvas().getBufferStrategy();
		if(bs == null) {
			Display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.fillRect(-1000, -1000, width+1000, height+1000);
		if (State.getState() != null) {
			State.getState().render(g);
		}
		
		bs.show();
		g.dispose();
	}
}
