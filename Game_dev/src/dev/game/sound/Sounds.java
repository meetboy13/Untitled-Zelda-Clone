package dev.game.sound;

public class Sounds {

	public static soundEffect hurt,collide;
	public static void init() {

		hurt = new soundEffect();
		hurt.setFile("Resources/Sound/hitsound.wav");

		collide = new soundEffect();
		collide.setFile("Resources/Sound/collidesound.wav");
		}

}
