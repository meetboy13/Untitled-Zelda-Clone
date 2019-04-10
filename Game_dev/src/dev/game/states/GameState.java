package dev.game.states;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.game;
import dev.game.creatures.Player;
import dev.game.entity.statics.tree;
import dev.game.tile.Tile;
import dev.game.worlds.World;
import dev.launcher.Assets;

public class GameState extends State{
	
	private Player player;
	private World world;
	private tree Tree;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler , "Resources/worlds/world1.txt");
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
