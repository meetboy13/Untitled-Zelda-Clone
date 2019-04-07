package dev.game.states;

import java.awt.Graphics;

import dev.game.game;
import dev.game.creatures.Player;
import dev.launcher.Assets;

public class GameState extends State{
	
	private Player player;
	
	public GameState(game Game) {
		super(Game);
		player= new Player(Game,0,0);
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		player.render(g);
	}

}
