package dev.game.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.game.Handler;
import dev.game.ui.ClickListener;
import dev.game.ui.UIImageButton;
import dev.game.ui.UIManager;
import dev.launcher.Assets;

public class MenuState  extends State{
	//constructor
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		super(handler);
		stateName="MenuState";
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(200,200,128,64,Assets.btn_start,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				State.setState(handler.getGame().gameState);
			}
		}));
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX()-4, handler.getMouseManager().getMouseY()-4, 8, 8);
		uiManager.render(g);
	}

}
