package dev.game.states;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.ui.ClickListener;
import dev.game.ui.UIImageButton;
import dev.game.ui.UIManager;
import dev.launcher.Assets;

public class GameOverState extends State{
	private UIManager uiManager;
	public GameOverState(Handler handler) {
		super(handler);
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
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		uiManager.render(g);
	}
	
}
