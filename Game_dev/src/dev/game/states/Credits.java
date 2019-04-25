package dev.game.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.game.Handler;
import dev.game.ui.ClickListener;
import dev.game.ui.UIImageButton;
import dev.game.ui.UIManager;
import dev.launcher.Assets;

public class Credits extends State{

	private UIManager uiManager;
	public Credits(Handler handler) {
		super(handler);
		stateName="Credits";
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(200,200,550,70,Assets.btn_start,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);				
				State menuState = new MenuState(handler);
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
		//g.drawImage(Assets, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9)
		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX()-4, handler.getMouseManager().getMouseY()-4, 8, 8);
		uiManager.render(g);
	}

}
