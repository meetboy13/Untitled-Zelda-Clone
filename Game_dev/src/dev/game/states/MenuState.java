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
		uiManager.addObject(new UIImageButton(226,299,550,70,Assets.btn_start,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				GameState gameState=new GameState(handler);
				State.setState(gameState);
			}
		}));
		uiManager.addObject(new UIImageButton(226,374,550,70,Assets.btn_high_score,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				HighScore highscore=new HighScore(handler);
				State.setState(highscore);
			}
		}));
		uiManager.addObject(new UIImageButton(226,450,550,70,Assets.btn_achieve,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				GameState gameState=new GameState(handler);
				State.setState(gameState);
			}
		}));
		uiManager.addObject(new UIImageButton(226,526,550,70,Assets.btn_exit,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				System.exit(1);
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
		//g.drawImage(Assets, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9)
		g.drawImage(Assets.title_screen, 0, 0, handler.getWidth(), handler.getHeight(), null);
		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX()-4, handler.getMouseManager().getMouseY()-4, 8, 8);
		uiManager.render(g);
	}

}
