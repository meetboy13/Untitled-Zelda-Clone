package dev.launcher;

import java.awt.image.BufferedImage;

import dev.ImageLoader.loader;

public class Assets {
	public static BufferedImage sprite1,sprite2,sprite3,sprite4;
	private static final int width=32,height=32;
	public static void init() {
		SpriteSheet sheet= new SpriteSheet(loader.loadImage("/Textures/background.jpg"));
		sprite1 = sheet.crop(0, 0, width, height);
		sprite2 = sheet.crop(width, 0, width, height);
		sprite3 = sheet.crop(width*2, 0, width, height);
		sprite4 = sheet.crop(width*3, 0, width, height);
	}
}
