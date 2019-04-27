package dev.launcher;

import java.awt.image.BufferedImage;

import dev.ImageLoader.Loader;
import dev.game.sound.soundEffect;

public class Assets {
	public static soundEffect hurt;
	public static BufferedImage 
	tree,water,grass,drop,dirt,gameOver,bridge_left,bridge_right,rock,magic,key,health,shield,
	wall_left,wall_right,wall_right_down,wall_left_down,wall_down,wall_up,wall_right_up,wall_left_up,
	stone,stone_wall_left,stone_wall_right,stone_wall_up,stone_wall_down,stone_wall_right_down,statue,
	stone_wall_left_down,stone_wall_left_up,stone_wall_right_up,stone_null, startUp, healthimproved,
	stone_wall_corner_right_down,stone_wall_corner_left_down,stone_wall_corner_left_up,stone_wall_corner_right_up,
	stone_wall_crown,stone_wall_eagle, wizard_stunned_up, wizard_stunned_down, wizard_stunned_left, wizard_stunned_right,
	carpet_left,carpet_right,carpet_left_up,carpet_left_down,carpet_right_up,carpet_right_down,
	sword_training,sword_mirror,sword_op;
	public static BufferedImage[] player_down,player_left,player_right,player_up,player_die,spear, friend_up,
	friend_right,friend_left,friend_down,player_throw_front, player_hurt, BossHead,BossHandLeft, BossHandRight, btn_start,wizard_beam,healthSpriteSheet,
	wizard_down,wizard_left,wizard_right,wizard_up,	player_throw_left,player_throw_right,player_throw_back,player_shield_up,player_shield_down,
	player_shield_right,player_shield_left,player_training_up,player_training_down,	player_training_left,stunbeamUp,stunbeamDown,
	player_training_right,player_mirror_up,player_mirror_down,player_mirror_right,player_mirror_left,stunbeamLeft,stunbeamRight,
	player_op_up,player_op_down,player_op_left,player_op_right,player_wand,wizard_float_down,wizard_float_left,wizard_float_right,wizard_float_up,
	wizard_attack_up,wizard_attack_left,wizard_attack_down,wizard_attack_right, sheep_walk_down,sheep_walk_right,sheep_walk_left,sheep_walk_up,
	bull_down,bull_up,bull_left,bull_right,bull_stunned,stun_indicator,friend_shield_left,friend_shield_right,friend_shield_up,friend_shield_down;
	private static final int width=100,height=100;//sprite sheet cell dimensions
	public static void init() {
		hurt = new soundEffect();
		hurt.setFile("Resources/Sound/hitsound.wav");
		SpriteSheet sheet0= new SpriteSheet(Loader.loadImage("/Sprite/BossHeadSpriteSheet.png"));
		SpriteSheet sheet1= new SpriteSheet(Loader.loadImage("/Textures/gem.png"));
		SpriteSheet sheet2= new SpriteSheet(Loader.loadImage("/Textures/GameOver1.png")); 
		SpriteSheet sheet3= new SpriteSheet(Loader.loadImage("/Textures/water.png"));
		SpriteSheet sheet4= new SpriteSheet(Loader.loadImage("/Textures/grass.png"));
		SpriteSheet sheet5= new SpriteSheet(Loader.loadImage("/Textures/PlayButton.png"));
		SpriteSheet sheet6= new SpriteSheet(Loader.loadImage("/Textures/PlayButtonSelected.png"));
		SpriteSheet sheet7= new SpriteSheet(Loader.loadImage("/Textures/rock.png"));
		SpriteSheet sheet8= new SpriteSheet(Loader.loadImage("/Sprite/wizard_beam1.png")); 
		SpriteSheet sheet9= new SpriteSheet(Loader.loadImage("/Sprite/wizard_beam2.png")); 
		SpriteSheet sheet10= new SpriteSheet(Loader.loadImage("/Textures/K13.png"));
		SpriteSheet sheet11= new SpriteSheet(Loader.loadImage("/Sprite/FriendSpriteSheet.png"));
		SpriteSheet sheet12= new SpriteSheet(Loader.loadImage("/Sprite/Shield.png"));
		SpriteSheet sheet13= new SpriteSheet(Loader.loadImage("/Textures/dirt.png"));
		SpriteSheet sheet14= new SpriteSheet(Loader.loadImage("/Textures/bridge_left.png"));
		SpriteSheet sheet15= new SpriteSheet(Loader.loadImage("/Textures/bridge_right.png"));
		SpriteSheet sheet16= new SpriteSheet(Loader.loadImage("/Sprite/Key.png"));
		SpriteSheet sheet17= new SpriteSheet(Loader.loadImage("/Textures/wall_left.png"));
		SpriteSheet sheet18= new SpriteSheet(Loader.loadImage("/Textures/wall_right.png"));
		SpriteSheet sheet19= new SpriteSheet(Loader.loadImage("/Textures/wall_down.png"));
		SpriteSheet sheet20= new SpriteSheet(Loader.loadImage("/Textures/wall_up.png"));
		SpriteSheet sheet21= new SpriteSheet(Loader.loadImage("/Textures/wall_left_down.png"));
		SpriteSheet sheet22= new SpriteSheet(Loader.loadImage("/Textures/wall_right_down.png"));
		SpriteSheet sheet23= new SpriteSheet(Loader.loadImage("/Textures/wall_left_up.png"));
		SpriteSheet sheet24= new SpriteSheet(Loader.loadImage("/Textures/wall_right_up.png"));
		SpriteSheet sheet25= new SpriteSheet(Loader.loadImage("/Sprite/tree.png"));
		SpriteSheet sheet26= new SpriteSheet(Loader.loadImage("/Sprite/PlayerSpriteSheet.png")); 
		SpriteSheet sheet27= new SpriteSheet(Loader.loadImage("/Sprite/spear.png")); 
		SpriteSheet sheet28= new SpriteSheet(Loader.loadImage("/Textures/stone.png")); 
		SpriteSheet sheet29= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_left.png")); 
		SpriteSheet sheet30= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_right.png")); 
		SpriteSheet sheet31= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_down.png"));  
		SpriteSheet sheet32= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_up.png"));  
		SpriteSheet sheet33= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_right_down.png"));  
		SpriteSheet sheet34= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_left_down.png"));
		SpriteSheet sheet35= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_right_up.png"));  
		SpriteSheet sheet36= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_left_up.png"));  
		SpriteSheet sheet37= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_corner_right_down.png"));  
		SpriteSheet sheet38= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_corner_left_down.png"));
		SpriteSheet sheet39= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_corner_right_up.png"));  
		SpriteSheet sheet40= new SpriteSheet(Loader.loadImage("/Textures/stone_wall_corner_left_up.png"));   
		SpriteSheet sheet41= new SpriteSheet(Loader.loadImage("/Textures/stone_null.png"));    
		SpriteSheet sheet42= new SpriteSheet(Loader.loadImage("/Textures/healthSpritesheet.png")); 
		SpriteSheet sheet43= new SpriteSheet(Loader.loadImage("/Textures/carpet_left.png")); 
		SpriteSheet sheet44= new SpriteSheet(Loader.loadImage("/Textures/carpet_right.png")); 
		SpriteSheet sheet45= new SpriteSheet(Loader.loadImage("/Textures/carpet_right_down.png"));  
		SpriteSheet sheet46= new SpriteSheet(Loader.loadImage("/Textures/carpet_left_down.png"));
		SpriteSheet sheet47= new SpriteSheet(Loader.loadImage("/Textures/carpet_right_top.png"));  
		SpriteSheet sheet48= new SpriteSheet(Loader.loadImage("/Textures/carpet_left_up.png"));  
		SpriteSheet sheet49= new SpriteSheet(Loader.loadImage("/Textures/stone_eagle_banner.png"));  
		SpriteSheet sheet50= new SpriteSheet(Loader.loadImage("/Textures/stone_crown_banner.png"));
		SpriteSheet sheet51= new SpriteSheet(Loader.loadImage("/Sprite/WizardSpriteSheet.png"));
		SpriteSheet sheet52= new SpriteSheet(Loader.loadImage("/Sprite/health pickup.png"));
		SpriteSheet sheet53= new SpriteSheet(Loader.loadImage("/Sprite/righthand.png"));
		SpriteSheet sheet54= new SpriteSheet(Loader.loadImage("/Sprite/lefthand.png"));
		SpriteSheet sheet55= new SpriteSheet(Loader.loadImage("/Sprite/SheepSpriteSheet.png"));
		SpriteSheet sheet56= new SpriteSheet(Loader.loadImage("/Sprite/BullSpriteSheet.png"));
		SpriteSheet sheet57= new SpriteSheet(Loader.loadImage("/Sprite/stunSpritesheet.png"));
		SpriteSheet sheet58= new SpriteSheet(Loader.loadImage("/Sprite/health 4 improved.png"));
		SpriteSheet sheet59= new SpriteSheet(Loader.loadImage("/Sprite/stunBeamSheet.png"));
		SpriteSheet sheet63= new SpriteSheet(Loader.loadImage("/Sprite/statue.png"));
		SpriteSheet sheet60= new SpriteSheet(Loader.loadImage("/Sprite/Training Sword.png"));
		SpriteSheet sheet61= new SpriteSheet(Loader.loadImage("/Sprite/Mirror Sword.png"));
		SpriteSheet sheet62= new SpriteSheet(Loader.loadImage("/Sprite/OP Sword.png"));
		
		
		BossHead = new BufferedImage[3];
		BossHead[0]=sheet0.crop(0, 0, 300, 200);
		BossHead[1]=sheet0.crop(0, 200, 300, 200);
		BossHead[2]=sheet0.crop(0, 400, 300, 200);
		
		BossHandLeft = new BufferedImage[5];
		BossHandRight= new BufferedImage[5];
		BossHandRight[0]= sheet53.crop(0, 0, width, height);
		BossHandRight[1]= sheet53.crop(0, height, width, height);
		BossHandRight[2]= sheet53.crop(0, height*2, width, height);
		BossHandRight[3]= sheet53.crop(0, height*3, width, height);
		BossHandRight[4]= sheet53.crop(0, height*4, width, height);
		BossHandLeft[0]= sheet54.crop(0, 0, width, height);
		BossHandLeft[1]= sheet54.crop(0, height, width, height);
		BossHandLeft[2]= sheet54.crop(0, height*2, width, height);
		BossHandLeft[3]= sheet54.crop(0, height*3, width, height);
		BossHandLeft[4]= sheet54.crop(0, height*4, width, height);
		
		statue=sheet63.crop(0, 0, width, height);
		drop=sheet1.crop(0,0,width,height);
		key=sheet16.crop(0, 0, width, height);
		shield=sheet12.crop(0, 0, 34, 33);
		health=sheet52.crop(0, 0, width, height);
		startUp = sheet10.crop(0, 0, 1024, 768);
		gameOver=sheet2.crop(0, 0, 1024, 768);
		rock=sheet7.crop(0, 0, 100, 100);
		wizard_beam=new BufferedImage[2];
		wizard_beam[0]=sheet8.crop(0,0,100,100);
		wizard_beam[1]=sheet9.crop(0,0,100,100);
		btn_start = new BufferedImage[2];
		btn_start[0]=sheet5.crop(0,0,550,70);
		btn_start[1]=sheet6.crop(0,0,550,70);
		healthSpriteSheet = new BufferedImage[5];
		healthSpriteSheet[0] = sheet42.crop(0,0, 50, 50);
		healthSpriteSheet[1] = sheet42.crop(50,0, 50, 50);
		healthSpriteSheet[2] = sheet42.crop(50*2,0, 50, 50);
		healthSpriteSheet[3] = sheet42.crop(50*3,0, 50, 50);
		healthSpriteSheet[4] = sheet42.crop(50*4,0, 50, 50);
		healthimproved = sheet58.crop(25,25,50,50);
		
		//player
		player_down = new BufferedImage[4];
		player_up = new BufferedImage[4];
		player_right = new BufferedImage[4];
		player_left = new BufferedImage[4];
		player_hurt = new BufferedImage[4];
		player_die = new BufferedImage[4];
		player_throw_front = new BufferedImage[4];
		player_throw_left = new BufferedImage[4];
		player_throw_right = new BufferedImage[4];
		player_throw_back = new BufferedImage[4];
		player_shield_up = new BufferedImage[4];
		player_shield_down = new BufferedImage[4];
		player_shield_right = new BufferedImage[2];
		player_shield_left = new BufferedImage[2];
		player_training_up = new BufferedImage[3];
		player_training_down = new BufferedImage[3];
		player_training_left = new BufferedImage[3];
		player_training_right = new BufferedImage[3];
		player_mirror_up = new BufferedImage[3];
		player_mirror_down = new BufferedImage[3];
		player_mirror_right = new BufferedImage[3];
		player_mirror_left = new BufferedImage[3];
		player_op_up = new BufferedImage[3];
		player_op_down = new BufferedImage[3];
		player_op_left = new BufferedImage[3];
		player_op_right = new BufferedImage[3];
		player_wand = new BufferedImage[4];

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
		player_die[0] = sheet26.crop(0,4*height, width, height);
		player_die[1] = sheet26.crop(width,4*height, width, height);
		player_die[2] = sheet26.crop(width*3,0, width, height);
		player_die[3] = sheet26.crop(width*2,4*height, width, height);
		player_hurt[0] = sheet26.crop(0,height*7, width, height);
		player_hurt[1] = sheet26.crop(width*1,height*3, width, height);
		player_hurt[2] = sheet26.crop(width*2,height*3, width, height);
		player_hurt[3] = sheet26.crop(width*3,0, width, height);		
		player_throw_front[0] = sheet26.crop(0,height*7, width, height);
		player_throw_front[1] = sheet26.crop(width*1,height*7, width, height);
		player_throw_front[2] = sheet26.crop(width*2,height*7, width, height);
		player_throw_front[3] = sheet26.crop(width*3,height*7, width, height);
		player_throw_right[0] = sheet26.crop(0,height*8, width, height);
		player_throw_right[1] = sheet26.crop(width*1,height*8, width, height);
		player_throw_right[2] = sheet26.crop(width*2,height*8, width, height);
		player_throw_right[3] = sheet26.crop(width*3,height*8, width, height);
		player_throw_left[0] = sheet26.crop(0,height*9, width, height);
		player_throw_left[1] = sheet26.crop(width*1,height*9, width, height);
		player_throw_left[2] = sheet26.crop(width*2,height*9, width, height);
		player_throw_left[3] = sheet26.crop(width*3,height*9, width, height);
		player_throw_back[0] = sheet26.crop(0,height*10, width, height);
		player_throw_back[1] = sheet26.crop(width*1,height*10, width, height);
		player_throw_back[2] = sheet26.crop(width*2,height*10, width, height);
		player_throw_back[3] = sheet26.crop(width*3,height*10, width, height);
		player_shield_down[1] = sheet26.crop(0,height*11, width, height);
		player_shield_down[0] = sheet26.crop(0,height*12, width, height);
		player_shield_down[2] = sheet26.crop(0,height*13, width, height);
		player_shield_down[3] = sheet26.crop(0,height*11, width, height);
		player_shield_up[1] = sheet26.crop(width*3,height*11, width, height);
		player_shield_up[0] = sheet26.crop(width*3,height*12, width, height);
		player_shield_up[2] = sheet26.crop(width*3,height*13, width, height);
		player_shield_up[3] = sheet26.crop(width*3,height*11, width, height);
		player_shield_right[0] = sheet26.crop(width,height*11, width, height);
		player_shield_right[1] = sheet26.crop(width,height*12, width, height);
		player_shield_left[0] = sheet26.crop(width*2,height*11, width, height);
		player_shield_left[1] = sheet26.crop(width*2,height*12, width, height);
		player_training_down[0] = sheet26.crop(0,height*14, width, height);
		player_training_down[1] = sheet26.crop(0,height*15, width, height);
		player_training_down[2] = sheet26.crop(0,height*16, width, height);
		player_training_left[0] = sheet26.crop(width,height*14, width, height);
		player_training_left[1] = sheet26.crop(width,height*15, width, height);
		player_training_left[2] = sheet26.crop(width,height*16, width, height);
		player_training_right[0] = sheet26.crop(width*2,height*14, width, height);
		player_training_right[1] = sheet26.crop(width*2,height*15, width, height);
		player_training_right[2] = sheet26.crop(width*2,height*16, width, height);
		player_training_up[0] = sheet26.crop(width*3,height*14, width, height);
		player_training_up[1] = sheet26.crop(width*3,height*15, width, height);
		player_training_up[2] = sheet26.crop(width*3,height*16, width, height);
		player_mirror_down[0] = sheet26.crop(0,height*17, width, height);
		player_mirror_down[1] = sheet26.crop(0,height*18, width, height);
		player_mirror_down[2] = sheet26.crop(0,height*19, width, height);
		player_mirror_left[0] = sheet26.crop(width,height*17, width, height);
		player_mirror_left[1] = sheet26.crop(width,height*18, width, height);
		player_mirror_left[2] = sheet26.crop(width,height*19, width, height);
		player_mirror_right[0] = sheet26.crop(width*2,height*17, width, height);
		player_mirror_right[1] = sheet26.crop(width*2,height*18, width, height);
		player_mirror_right[2] = sheet26.crop(width*2,height*19, width, height);
		player_mirror_up[0] = sheet26.crop(width*3,height*17, width, height);
		player_mirror_up[1] = sheet26.crop(width*3,height*18, width, height);
		player_mirror_up[2] = sheet26.crop(width*3,height*19, width, height);
		player_op_down[0] = sheet26.crop(0,height*20, width, height);
		player_op_down[1] = sheet26.crop(0,height*21, width, height);
		player_op_down[2] = sheet26.crop(0,height*22, width, height);
		player_op_left[0] = sheet26.crop(width,height*20, width, height);
		player_op_left[1] = sheet26.crop(width,height*21, width, height);
		player_op_left[2] = sheet26.crop(width,height*22, width, height);
		player_op_right[0] = sheet26.crop(width*3,height*20, width, height);
		player_op_right[1] = sheet26.crop(width*3,height*21, width, height);
		player_op_right[2] = sheet26.crop(width*3,height*22, width, height);
		player_op_up[0] = sheet26.crop(width*2,height*20, width, height);
		player_op_up[1] = sheet26.crop(width*2,height*21, width, height);
		player_op_up[2] = sheet26.crop(width*2,height*22, width, height);
		player_wand[0] = sheet26.crop(0,height*22, width, height);
		player_wand[1] = sheet26.crop(width,height*22, width, height);
		player_wand[2] = sheet26.crop(width*3,height*22, width, height);
		player_wand[3] = sheet26.crop(width*2,height*22, width, height);

		//friend
		friend_down = new BufferedImage[4];
		friend_up = new BufferedImage[4];
		friend_left = new BufferedImage[4];
		friend_right = new BufferedImage[4];
		friend_shield_left = new BufferedImage[2];
		friend_shield_right = new BufferedImage[2];
		friend_shield_up = new BufferedImage[4];
		friend_shield_down = new BufferedImage[4];
		
		
		friend_down[1] = sheet11.crop(0, 0, 130, 130);
		friend_down[2] = sheet11.crop(0, 130, 130, 130);
		friend_down[3] = sheet11.crop(0, 0, 130, 130);
		friend_down[0] = sheet11.crop(0, 260, 130, 130);
		friend_up[1] = sheet11.crop(390, 0, 130, 130);
		friend_up[2] = sheet11.crop(390, 130, 130, 130);
		friend_up[3] = sheet11.crop(390, 0, 130, 130);
		friend_up[0] = sheet11.crop(390, 260, 130, 130);
		friend_left[1] = sheet11.crop(130, 0, 130, 130);
		friend_left[2] = sheet11.crop(130, 130, 130, 130);
		friend_left[3] = sheet11.crop(130, 0, 130, 130);
		friend_left[0] = sheet11.crop(130, 260, 130, 130);
		friend_right[0] = sheet11.crop(260, 260, 130, 130);
		friend_right[1] = sheet11.crop(260, 0, 130, 130);
		friend_right[2] = sheet11.crop(260, 130, 130, 130);
		friend_right[3] = sheet11.crop(260, 0, 130, 130);
		friend_shield_right[0] = sheet11.crop(260, 130*9, 130, 130);
		friend_shield_right[1] = sheet11.crop(260, 130*10, 130, 130);
		friend_shield_up[0] = sheet11.crop(390, 130*10, 130, 130);
		friend_shield_up[1] = sheet11.crop(390, 130*9, 130, 130);
		friend_shield_up[2] = sheet11.crop(390, 130*11, 130, 130);
		friend_shield_up[3] = sheet11.crop(390, 130*9, 130, 130);
		friend_shield_down[0] = sheet11.crop(0, 130*10, 130, 130);
		friend_shield_down[1] = sheet11.crop(0, 130*9, 130, 130);
		friend_shield_down[2] = sheet11.crop(0, 130*11, 130, 130);
		friend_shield_down[3] = sheet11.crop(0, 130*9, 130, 130);
		friend_shield_left[0] = sheet11.crop(130, 130*9, 130, 130);
		friend_shield_left[1] = sheet11.crop(130, 1300, 130, 130);
		
		//projectiles
		spear = new BufferedImage[4];
		spear[0] = sheet27.crop(0, 0, width, height);
		spear[1] = sheet27.crop(width, 0, width, height);
		spear[2] = sheet27.crop(width*2, 0, width, height);
		spear[3] = sheet27.crop(width*3, 0, width, height);

		stunbeamUp= new BufferedImage[3];
		stunbeamLeft= new BufferedImage[3];
		stunbeamRight= new BufferedImage[3];
		stunbeamDown= new BufferedImage[3];
		stunbeamUp[0] = sheet59.crop(0, 0, width, height);
		stunbeamUp[1] = sheet59.crop(width, 0, width, height);
		stunbeamUp[2] = sheet59.crop(width*2, 0, width, height);
		stunbeamDown[0] = sheet59.crop(0, height, width, height);
		stunbeamDown[1] = sheet59.crop(width, height, width, height);
		stunbeamDown[2] = sheet59.crop(width*2, height, width, height);
		stunbeamLeft[0] = sheet59.crop(0, height*2, width, height);
		stunbeamLeft[1] = sheet59.crop(width, height*2, width, height);
		stunbeamLeft[2] = sheet59.crop(width*2, height*2, width, height);
		stunbeamRight[0] = sheet59.crop(0, height*3, width, height);
		stunbeamRight[1] = sheet59.crop(width, height*3, width, height);
		stunbeamRight[2] = sheet59.crop(width*2, height*3, width, height);
		
		magic = sheet8.crop(0,0,width,height);
		
		water = sheet3.crop(0, 0, 1000, 1000);
		grass = sheet4.crop(0, 0, 1000, 1000);
		dirt = sheet13.crop(0, 0, 1000, 1000);
		bridge_left = sheet14.crop(0, 0, 100, 100);
		bridge_right = sheet15.crop(0, 0, 100, 100);
		wall_left = sheet17.crop(0, 0, 1000, 1000);
		wall_right = sheet18.crop(0, 0, 1000, 1000);
		wall_down = sheet19.crop(0, 0, 1000, 1000);
		wall_up = sheet20.crop(0, 0, 1000, 1000);
		wall_left_down = sheet21.crop(0, 0, 1000, 1000);
		wall_right_down = sheet22.crop(0, 0, 1000, 1000);
		wall_left_up = sheet23.crop(0, 0, 1000, 1000);
		wall_right_up = sheet24.crop(0, 0, 1000, 1000);
		tree = sheet25.crop(0,0,150,200);
		stone = sheet28.crop(0, 0, 100, 100);
		stone_wall_left = sheet29.crop(0, 0, 100, 100);
		stone_wall_right = sheet30.crop(0, 0, 100, 100);
		stone_wall_down = sheet31.crop(0, 0, 100, 100);
		stone_wall_up = sheet32.crop(1, 0, 99, 100);
		stone_wall_right_down = sheet33.crop(0, 0, 1000, 1000);
		stone_wall_left_down = sheet34.crop(0, 0, 1000, 1000);
		stone_wall_right_up = sheet35.crop(1, 0, 99, 100);
		stone_wall_left_up = sheet36.crop(0, 1, 100, 99);
		stone_wall_corner_right_down = sheet37.crop(1, 0, 99, 100);
		stone_wall_corner_left_down = sheet38.crop(0, 0, 100, 100);
		stone_wall_corner_right_up = sheet39.crop(0, 0, 100, 100);
		stone_wall_corner_left_up = sheet40.crop(0, 0, 100, 100);
		stone_null = sheet41.crop(0, 0, 100, 100);
		
		carpet_left = sheet43.crop(0, 1, 99, 98);
		carpet_right = sheet44.crop(0, 1, 99, 99);
		carpet_right_down = sheet45.crop(1, 1, 98, 99);
		carpet_left_down = sheet46.crop(0, 1, 100, 99);
		carpet_right_up = sheet47.crop(0, 0, 99, 99);
		carpet_left_up = sheet48.crop(0, 0, 99, 99);
		
		stone_wall_eagle = sheet49.crop(0, 0, 99, 99);
		stone_wall_crown = sheet50.crop(0, 0, 99, 99);
		
		//wizard
		wizard_down=new BufferedImage[3];
		wizard_down[0] = sheet51.crop(0, 0, width, height);
		wizard_down[1] = sheet51.crop(0, height, width, height);
		wizard_down[2] = sheet51.crop(0, 2*height, width, height);
		
		wizard_float_down=new BufferedImage[3];
		wizard_float_down[0] = sheet51.crop(0, 3*height, width, height);
		wizard_float_down[1] = sheet51.crop(0, 4*height, width, height);
		wizard_float_down[2] = sheet51.crop(0, 5*height, width, height);
		
		wizard_attack_down=new BufferedImage[4];
		wizard_attack_down[0] = sheet51.crop(0, 6*height, width, height);
		wizard_attack_down[1] = sheet51.crop(0, 7*height, width, height);
		wizard_attack_down[2] = sheet51.crop(0, 8*height, width, height);
		wizard_attack_down[3] = sheet51.crop(0, 9*height, width, height);
		
		wizard_stunned_down = sheet51.crop(0, 10*height, width, height);
		
		wizard_left=new BufferedImage[3];
		wizard_left[0] = sheet51.crop(width, 0, width, height);
		wizard_left[1] = sheet51.crop(width, height, width, height);
		wizard_left[2] = sheet51.crop(width, 2*height, width, height);
		//wizard_left[3] = sheet51.crop(width, height, width, height);
		
		wizard_float_left=new BufferedImage[3];
		wizard_float_left[0] = sheet51.crop(width, 3*height, width, height);
		wizard_float_left[1] = sheet51.crop(width, 4*height, width, height);
		wizard_float_left[2] = sheet51.crop(width, 5*height, width, height);
		//wizard_float_left[3] = sheet51.crop(width, 4*height, width, height);
		
		wizard_attack_left=new BufferedImage[4];
		wizard_attack_left[0] = sheet51.crop(width, 6*height, width, height);
		wizard_attack_left[1] = sheet51.crop(width, 7*height, width, height);
		wizard_attack_left[2] = sheet51.crop(width, 8*height, width, height);
		wizard_attack_left[3] = sheet51.crop(width, 9*height, width, height);
		
		wizard_stunned_left = sheet51.crop(width, 10*height, width, height);
		
		wizard_right=new BufferedImage[4];
		wizard_right[0] = sheet51.crop(width*2, height, width, height);
		wizard_right[1] = sheet51.crop(width*2, 0, width, height);
		wizard_right[2] = sheet51.crop(width*2, 2*height, width, height);
		wizard_right[3] = sheet51.crop(width*2, 0, width, height);
		
		wizard_float_right = new BufferedImage[4];
		wizard_float_right[0] = sheet51.crop(width*2+10, 4*height, width, height);
		wizard_float_right[1] = sheet51.crop(width*2, 3*height, width, height);
		wizard_float_right[2] = sheet51.crop(width*2, 5*height, width, height);
		wizard_float_right[3] = sheet51.crop(width*2, 3*height, width, height);

		wizard_attack_right = new BufferedImage[4];
		wizard_attack_right[0] = sheet51.crop(width*2, 6*height, width, height);
		wizard_attack_right[1] = sheet51.crop(width*2, 7*height, width, height);
		wizard_attack_right[2] = sheet51.crop(width*2, 8*height, width, height);
		wizard_attack_right[3] = sheet51.crop(width*2, 9*height, width, height);
		
		wizard_stunned_right = sheet51.crop(width*2, 10*height, width, height);
		
		wizard_up=new BufferedImage[3];
		wizard_up[0] = sheet51.crop(width*3, 0, width, height);
		wizard_up[1] = sheet51.crop(width*3, height, width, height);
		wizard_up[2] = sheet51.crop(width*3, 2*height, width, height);
		
		wizard_float_up = new BufferedImage[3];
		wizard_float_up[0] = sheet51.crop(width*3, 3*height, width, height);
		wizard_float_up[1] = sheet51.crop(width*3, 4*height, width, height);
		wizard_float_up[2] = sheet51.crop(width*3, 5*height, width, height);
		
		wizard_attack_up = new BufferedImage[4];
		wizard_attack_up[0] = sheet51.crop(width*3, 6*height, width, height);
		wizard_attack_up[1] = sheet51.crop(width*3, 7*height, width, height);
		wizard_attack_up[2] = sheet51.crop(width*3, 8*height, width, height);
		wizard_attack_up[3] = sheet51.crop(width*3, 9*height, width, height);
		
		wizard_stunned_up = sheet51.crop(width*3, 0, width, height);
		
		//sheep
		sheep_walk_down = new BufferedImage[4];
		sheep_walk_left = new BufferedImage[4];
		sheep_walk_right = new BufferedImage[4];
		sheep_walk_up = new BufferedImage[4];

		sheep_walk_down[0] = sheet55.crop(0, height, width, height);
		sheep_walk_down[1] = sheet55.crop(0, 0, width, height);
		sheep_walk_down[2] = sheet55.crop(0, height*2, width, height);
		sheep_walk_down[3] = sheet55.crop(0, 0, width, height);
		sheep_walk_left[0] = sheet55.crop(width, height, width, height);
		sheep_walk_left[1] = sheet55.crop(width, 0, width, height);
		sheep_walk_left[2] = sheet55.crop(width, height*2, width, height);
		sheep_walk_left[3] = sheet55.crop(width, 0, width, height);	
		sheep_walk_right[0] = sheet55.crop(width*2, height, width, height);
		sheep_walk_right[1] = sheet55.crop(width*2, 0, width, height);
		sheep_walk_right[2] = sheet55.crop(width*2, height*2, width, height);
		sheep_walk_right[3] = sheet55.crop(width*2, 0, width, height);		
		sheep_walk_up[0] = sheet55.crop(width*3, height, width, height);
		sheep_walk_up[1] = sheet55.crop(width*3, 0, width, height);
		sheep_walk_up[2] = sheet55.crop(width*3, height*2, width, height);
		sheep_walk_up[3] = sheet55.crop(width*3, 0, width, height);
		
		//bull
		bull_down = new BufferedImage[4];
		bull_left = new BufferedImage[4];
		bull_right = new BufferedImage[4];
		bull_up = new BufferedImage[4];
		bull_stunned = new BufferedImage[4];

		bull_down[0] = sheet56.crop(0, height, width, height);
		bull_down[1] = sheet56.crop(0, 0, width, height);
		bull_down[2] = sheet56.crop(0, height*2, width, height);
		bull_down[3] = sheet56.crop(0, 0, width, height);
		bull_left[0] = sheet56.crop(width, height, width, height);
		bull_left[1] = sheet56.crop(width, 0, width, height);
		bull_left[2] = sheet56.crop(width, height*2, width, height);
		bull_left[3] = sheet56.crop(width, 0, width, height);	
		bull_right[0] = sheet56.crop(width*2, height, width, height);
		bull_right[1] = sheet56.crop(width*2, 0, width, height);
		bull_right[2] = sheet56.crop(width*2, height*2, width, height);
		bull_right[3] = sheet56.crop(width*2, 0, width, height);		
		bull_up[0] = sheet56.crop(width*3, height, width, height);
		bull_up[1] = sheet56.crop(width*3, 0, width, height);
		bull_up[2] = sheet56.crop(width*3, height*2, width, height);
		bull_up[3] = sheet56.crop(width*3, 0, width, height);
		bull_stunned[0] = sheet56.crop(0, height*3, width, height);
		bull_stunned[1] = sheet56.crop(width, height*3, width, height);
		bull_stunned[2] = sheet56.crop(width*2, height*3, width, height);
		bull_stunned[3] = sheet56.crop(width*3, 0, width, height);
		
		//stun
		stun_indicator = new BufferedImage[3];
		stun_indicator[0] = sheet57.crop(0, 0, width, height); 
		stun_indicator[1] = sheet57.crop(width, 0, width, height); 
		stun_indicator[2] = sheet57.crop(width*2, 0, width, height); 

		sword_training = sheet60.crop(0, 0, width, height); 
		sword_mirror = sheet61.crop(0, 0, width, height); 
		sword_op = sheet62.crop(0, 0, width, height); 
	}
}
