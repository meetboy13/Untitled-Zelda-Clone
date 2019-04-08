package dev.game.states;

import java.awt.Graphics;

import dev.game.game;
import dev.game.creatures.Player;
import dev.game.tile.Tile;
import dev.game.worlds.World;
import dev.launcher.Assets;

public class GameState extends State{
	
	private Player player;
	private World world;
	
	public GameState(game Game) {
		super(Game);
		player= new Player(Game,0,0, 0, 0);
		world = new World(Game,"Resources/worlds/world1.txt");
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		world.tick();
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
		player.render(g);
	}

}
