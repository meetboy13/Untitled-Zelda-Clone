package dev.game;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.display.*;
import dev.game.input.KeyManager;
import dev.game.input.MouseManager;
import dev.game.sound.Sounds;
import dev.game.states.MenuState;
import dev.game.states.StartState;
import dev.game.states.State;
import dev.launcher.Assets;
import dev.launcher.GameCamera;

public class Game implements Runnable{
	private Display display;
	private Thread thread;
	public String title;
	private int width, height;
	private BufferStrategy bs;
	private Graphics g;
	private boolean running;
	public State menuState;
	public State startState;
	private KeyManager KeyManager;
	
	private MouseManager mouseManager;
	
	private GameCamera gameCamera;
	
	private Handler handler;
	
	//Game constructor
	public Game(String title, int width, int height) {
		this.width = width;
		this.height=height;
		this.title=title;
		KeyManager = new KeyManager();
		mouseManager=new MouseManager();
	}
	
	//initialisation function
	private void init() {
		display = new Display(title,width,height);
		display.getFrame().addKeyListener(KeyManager);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		//call the asset initialisation function
		Assets.init();
		Sounds.init();
		handler = new Handler(this);
		gameCamera = new GameCamera(handler,0,0);
		menuState = new MenuState(handler);
		startState = new StartState(handler);
		
		//set initial gamestate
		State.setState(menuState);
		//State.setState(startState);
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
				try {
					Thread.sleep(12);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			//the 3 is how many frames the output is delayed for.
			//drawing directly to the screen may cause visual glitches
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		//clears screen
		g.clearRect(0, 0, width, height);
		
		if (State.getState() != null) {
			State.getState().render(g);
		}

		//get rid of the excess variables to save memory
		bs.show();
		g.dispose();
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
	
	public KeyManager getKeyManager() {
		return KeyManager;
	}
	public MouseManager getMouseManager() {
		return mouseManager;
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
	
	
}
