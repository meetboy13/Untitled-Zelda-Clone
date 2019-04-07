package dev.launcher;

import java.awt.image.BufferedImage;

import dev.ImageLoader.loader;

public class Assets {
	public static BufferedImage sprite1,sprite2,sprite3,sprite4,player;
	private static final int width=32,height=32;
	public static void init() {
		SpriteSheet sheet1= new SpriteSheet(loader.loadImage("/Textures/background.jpg"));
		SpriteSheet sheet2= new SpriteSheet(loader.loadImage("/Textures/square.png"));
		sprite1 = sheet1.crop(0, 0, width, height);
		sprite2 = sheet1.crop(width, 0, width, height);
		sprite3 = sheet1.crop(width*2, 0, width, height);
		sprite4 = sheet1.crop(width*3, 0, width, height);
		player = sheet2.crop(0, 0, width, height);
	}
}
