package dev.game;



import dev.game.input.KeyManager;
import dev.game.worlds.World;
import dev.launcher.GameCamera;
import dev.game.input.MouseManager;

public class Handler {
	private Game game;
	private World world;
	//constructor
	public Handler (Game Game) {
		this.game=Game;
	}
	//setters and getters
	public int getWidth() {
		return game.getWidth();
	}
	public int getHeight() {
		return game.getHeight();
	}
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
}
