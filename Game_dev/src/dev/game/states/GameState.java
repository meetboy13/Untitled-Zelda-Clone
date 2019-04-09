package dev.game.states;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.game;
import dev.game.creatures.Player;
import dev.game.tile.Tile;
import dev.game.worlds.World;
import dev.launcher.Assets;

public class GameState extends State{
	
	private Player player;
	private World world;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler , "Resources/worlds/world1.txt");
		handler.setWorld(world);
		player= new Player(handler,handler.getWorld().getSpawnX()*Tile.TILEWIDTH,handler.getWorld().getSpawnY()*Tile.TILEHEIGHT,0,0);
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
