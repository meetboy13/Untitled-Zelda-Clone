package dev.game.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.game.Handler;
import dev.game.ui.ClickListener;
import dev.game.ui.UIImageButton;
import dev.game.ui.UIManager;
import dev.launcher.Assets;

public class StartState  extends State{
	//constructor
	private UIManager uiManager;
	private long initialTime = 0, timeToStart=50, timeElapsed=0;
	
	public StartState(Handler handler) {
		super(handler);
		stateName="StartState";
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		timeElapsed+=System.currentTimeMillis()-initialTime;
		initialTime=System.currentTimeMillis();

		if (timeElapsed<timeToStart) {
			State.setState(handler.getGame().menuState);
		}
		timeElapsed++;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.startUp, 0, 0, 1024, 768, null);
	}

}
