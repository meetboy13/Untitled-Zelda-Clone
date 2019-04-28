package dev.game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.game.ui.ClickListener;
import dev.game.ui.UIImageButton;
import dev.game.ui.UIManager;
import dev.game.utils.Utils;
import dev.launcher.Assets;

public class HighScore extends State{

	private UIManager uiManager;
	private String[] names;
	private int[] scores;
	public HighScore(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
		stateName="HighScoreState";
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUiManager(uiManager);
		uiManager.addObject(new UIImageButton(226,500,550,70,Assets.btn_title,new ClickListener() {
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				handler.getMouseManager().setUiManager(null);
				MenuState menuState=new MenuState(handler);
				State.setState(menuState);
			}
		}));
		names=new String[6];
		scores=new int[6];
		loadHighScores("Resources/HighScores/HighScores.txt");
	}

	private void loadHighScores(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		for (int y=0;y<5;y++) {
			names[y]=(tokens[(y*2)]);
			scores[y]=((Utils.parseInt(tokens[(y*2+1)])));
		}
	}
	public void checkHighScore(int score) {
		String name="Player_name";
		loadHighScores("Resources/HighScores/HighScores.txt");
		if(score>this.scores[0]) {
			for(int i=5;i>1;i-- ) {
				this.scores[i]=this.scores[i-1];
			}
			this.names[0]=name;
			this.scores[0]=score;
		}else {
			for(int i=1;i<5;i++) {
				if(score>this.scores[i]) {
					for(int j=5;j>i;j-- ) {
						this.scores[j]=this.scores[j-1];
						this.names[i]=this.names[j-1];
					}
					this.names[i]=name;
					this.scores[i]=score;
					break;
				}
				if(i==4) {
					this.names[5]=name;
					this.scores[5]=score;
				}
			}
		}
		String data="";
		for (int y=0;y<5;y++) {
			data=data+names[y]+" "+scores[y]+"\n";
		}
		try {
			Utils.saveFileAsString("Resources/HighScores/HighScores.txt",data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Font f = new Font("Courier", Font.PLAIN,20);
		g.setColor(Color.BLACK);
		g.setFont(f);
		for (int y=0;y<5;y++) {
			g.drawString(names[y]+":      "+scores[y], (int) (handler.getWidth()/2-100), 200+y*45);
		}
		uiManager.render(g);
	}

}
