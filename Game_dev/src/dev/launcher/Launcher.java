package dev.launcher;

import dev.game.Game;

public class Launcher {
	private static int width,height;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		width=1024;
		height=700;
		Game game= new Game("Window name",width,height);
		game.start();
	}

}
