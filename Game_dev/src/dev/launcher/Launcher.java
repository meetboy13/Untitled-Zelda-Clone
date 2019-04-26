package dev.launcher;

import java.io.IOException;

import dev.game.Game;
import dev.game.utils.Utils;
import dev.game.sound.Music;

public class Launcher {
	private static int width,height;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		width=1024;
		height=600;
		Game game= new Game("Untitled Game",width,height);
		game.start();
		//Music music = new Music();
		//music.setFile("Resources/Sound/background.wav");
		//music.loop();
		//music.play();
	}

}
