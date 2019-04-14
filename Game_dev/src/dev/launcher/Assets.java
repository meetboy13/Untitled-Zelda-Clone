package dev.launcher;

import java.awt.image.BufferedImage;

import dev.ImageLoader.Loader;

public class Assets {
	public static BufferedImage sprite1,sprite2,sprite3,tree,player,rock,grass,drop,dirt,wall_left,wall_right,wall_right_down,wall_left_down,wall_down,wall_up;
	public static BufferedImage[] player_down,player_left,player_right,player_up;
	public static BufferedImage[] btn_start;
	private static final int width=100,height=100;//sprite sheet cell dimensions
	public static void init() {
		SpriteSheet sheet1= new SpriteSheet(Loader.loadImage("/Textures/gem.png"));
		SpriteSheet sheet3= new SpriteSheet(Loader.loadImage("/Textures/rock.png"));
		SpriteSheet sheet4= new SpriteSheet(Loader.loadImage("/Textures/grass.png"));
		SpriteSheet sheet13= new SpriteSheet(Loader.loadImage("/Textures/dirt.png"));
		SpriteSheet sheet14= new SpriteSheet(Loader.loadImage("/Textures/Player_standing_back.png"));
		SpriteSheet sheet15= new SpriteSheet(Loader.loadImage("/Textures/Player_standing_right.png"));
		SpriteSheet sheet16= new SpriteSheet(Loader.loadImage("/Textures/Player_standing_left.png"));
		SpriteSheet sheet17= new SpriteSheet(Loader.loadImage("/Textures/wall_left.png"));
		SpriteSheet sheet18= new SpriteSheet(Loader.loadImage("/Textures/wall_right.png"));
		SpriteSheet sheet19= new SpriteSheet(Loader.loadImage("/Textures/wall_down.png"));
		SpriteSheet sheet20= new SpriteSheet(Loader.loadImage("/Textures/wall_up.png"));
		SpriteSheet sheet21= new SpriteSheet(Loader.loadImage("/Textures/wall_left_down.png"));
		SpriteSheet sheet22= new SpriteSheet(Loader.loadImage("/Textures/wall_right_down.png"));
		SpriteSheet sheet25= new SpriteSheet(Loader.loadImage("/Textures/tree.png"));
		SpriteSheet sheet26= new SpriteSheet(Loader.loadImage("/Textures/PlayerSpriteSheet.png")); 
		drop=sheet1.crop(0,0,100,100);
		btn_start = new BufferedImage[2];
		btn_start[0]=sheet3.crop(0,0,width,height);
		btn_start[1]=sheet4.crop(0,0,width,height);
		player_down = new BufferedImage[4];
		player_up = new BufferedImage[4];
		player_right = new BufferedImage[4];
		player_left = new BufferedImage[4];
		player_down[1] = sheet26.crop(0,0, width, height);
		player_down[3] = sheet26.crop(0,0, width, height);
		player_right[1] = sheet26.crop(width,0, width, height);
		player_right[3] = sheet26.crop(width,0, width, height);
		player_left[1] = sheet26.crop(width*2,0, width, height);
		player_left[3] = sheet26.crop(width*2,0, width, height);
		player_up[1] = sheet26.crop(width*3,0, width, height);
		player_up[3] = sheet26.crop(width*3,0, width, height);
		player_down[0]=sheet26.crop(0,height,width,height);
		player_right[0]=sheet26.crop(width,height,width,height);
		player_left[0]=sheet26.crop(width*2,height,width,height);
		player_up[0]=sheet26.crop(width*3,height,width,height);
		player_down[2]=sheet26.crop(0,2*height,width,height);
		player_right[2]=sheet26.crop(width,2*height,width,height);
		player_left[2]=sheet26.crop(width*2,2*height,width,height);
		player_up[2]=sheet26.crop(width*3,2*height,width,height);
		/*
		sprite1 = sheet1.crop(0, 0, width, height);
		sprite2 = sheet1.crop(width, 0, width, height);
		sprite3 = sheet1.crop(width*2, 0, width, height);
		*/
		player = sheet26.crop(0, 0, width, height);
		rock = sheet3.crop(0, 0, 259, 194);
		grass = sheet4.crop(0, 0, 1000, 1000);
		dirt = sheet13.crop(0, 0, 1000, 1000);
		wall_left = sheet17.crop(0, 0, 1000, 1000);
		wall_right = sheet18.crop(0, 0, 1000, 1000);
		wall_down = sheet19.crop(0, 0, 1000, 1000);
		wall_up = sheet20.crop(0, 0, 1000, 1000);
		wall_left_down = sheet21.crop(0, 0, 1000, 1000);
		wall_right_down = sheet22.crop(0, 0, 1000, 1000);
		tree = sheet25.crop(0,0,150,200);
	}
}
