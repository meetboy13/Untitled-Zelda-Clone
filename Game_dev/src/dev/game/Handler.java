package dev.game;



import dev.game.input.Keymanager;
import dev.game.worlds.World;
import dev.launcher.GameCamera;

public class Handler {
	private game Game;
	private World world;
	
	public Handler (game Game) {
		this.Game=Game;
		
	}
	public int getWidth() {
		return Game.getWidth();
	}
	public int getHeight() {
		return Game.getHeight();
	}
	public GameCamera getGameCamera() {
		return Game.getGameCamera();
	}
	public Keymanager getKeyManager() {
		return Game.getKeyManager();
	}
	public game getGame() {
		return Game;
	}
	public void setGame(game game) {
		Game = game;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
}
