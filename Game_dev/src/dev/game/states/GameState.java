package dev.game.states;

import java.awt.Graphics;

import dev.game.Handler;
import dev.game.HUD.HUD;
import dev.game.creatures.Player;
import dev.game.tile.Tile;
import dev.game.worlds.World;
import dev.game.worlds.World.WorldType;
import dev.launcher.Assets;

public class GameState extends State{
	
	private World world;
	private HUD hud;
	//constructor
	public GameState(Handler handler) {
		super(handler);
		stateName="GameState";
		world = new World(handler , "Resources/worlds/world1.txt","Resources/entities/world1.txt",WorldType.NORMAL);
		handler.setWorld(world);
		hud=new HUD(handler);
		handler.setHUD(hud);
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		world.tick();
		hud.tick();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		world.render(g);
		hud.render(g);
	}

}
