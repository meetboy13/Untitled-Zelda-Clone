package dev.game.sound;

public class Sounds {

	public static soundEffect hurt;
	public static Music music;
	public static void init() {

		hurt = new soundEffect();
		hurt.setFile("Resources/Sound/hitsound.wav");
		
		}

}
