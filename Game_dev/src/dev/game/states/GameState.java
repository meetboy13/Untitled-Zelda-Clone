package dev.game.states;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.creatures.Player;
import dev.game.tile.Tile;
import dev.game.worlds.World;
import dev.launcher.Assets;

public class GameState extends State{
	
	private World world;
	//constructor
	public GameState(Handler handler) {
		super(handler);
		stateName="GameState";
		world = new World(handler , "Resources/worlds/world1.txt","Resources/entities/world1.txt");
		handler.setWorld(world);
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
	}

}
