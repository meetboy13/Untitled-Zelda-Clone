package dev.launcher;

import java.io.IOException;

import dev.game.Game;
import dev.game.utils.Utils;

public class Launcher {
	private static int width,height;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		width=1024;
		height=600;
		Game game= new Game("Untitled Game",width,height);
		game.start();
	}

}
