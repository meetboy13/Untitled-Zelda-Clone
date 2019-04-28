package dev.game.states;

import java.awt.Color;
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
		// TODO Auto-generated constructor stub
		stateName="GameOverState";
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(237,47,550,70,Assets.btn_start,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				State gameState = new GameState(handler);
				State.setState(gameState);
			}
		}));
		uiManager.addObject(new UIImageButton(237,131,550,70,Assets.btn_title,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				MenuState menuState=new MenuState(handler);
				State.setState(menuState);
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
		g.drawImage(Assets.gameOver, 0, 0, 1024, 768,null);
		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX()-4, handler.getMouseManager().getMouseY()-4, 8, 8);
		uiManager.render(g);
	}
	
}
