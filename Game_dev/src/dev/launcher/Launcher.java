package dev.launcher;

import dev.game.Game;
import dev.game.sound.Music;

public class Launcher {
	private static int width,height;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		width=1024;
		height=768;
		Game game= new Game("Untitled Zelda Clone",width,height);
		game.start();
		Music.playSound("Resources/Sound/background.wav");
	}

}
