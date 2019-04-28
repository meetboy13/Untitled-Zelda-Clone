package dev.game.creatures;




import dev.game.Handler;
import dev.game.inventory.Inventory;
import dev.launcher.Animation;
import dev.launcher.Assets;
public class Player2 extends Player{

	private Animation animDown,animUp,animLeft,animRight,animDie,animDownT,animUpT,animLeftT,animRightT;

	private Inventory inventory;
	private boolean dead = false;
	public Player2(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH,Creature.DEFAULT_CREATURE_HEIGHT);
		// TODO Auto-generated constructor stub
		damage=1;
		bounds.x=16;
		bounds.y=32;
		bounds.width=32;
		bounds.height=32;
		speed=Creature.DEFAULT_SPEED;
		maxHealth=0;
		this.health=maxHealth;
		//animations
		animDown = new Animation(150,Assets.player_down);
		animLeft = new Animation(150,Assets.player_left);
		animUp = new Animation(150,Assets.player_up);
		animRight = new Animation(150,Assets.player_right);
		animDie = new Animation(100,Assets.player_die);
		animDownT = new Animation(150,Assets.friend_down);
		animLeftT = new Animation(150,Assets.friend_left);
		animUpT = new Animation(150,Assets.friend_up);
		animRightT = new Animation(150,Assets.friend_right);
		inventory = new Inventory(handler);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		animDownT.tick();
		animUpT.tick();
		animRightT.tick();
		animLeftT.tick();
		stunDecay();
		flickerDecay();
		if (!dead) {
			if(!stunned) {///change later so the player can still pause
				getInput();
			}
			move();
		}else {
			animDie.tick();
		}
		handler.getGameCamera().centeronEntity(this);
		inventory.tick();
		if(isTransformable()) {
			corruptionTick();
		}

	}
	private void getInput() {
		xMove=0;
		yMove=0;

		if(handler.getKeyManager().P2up && handler.getKeyManager().P2right) {
			yMove= (float) (-speed/Math.sqrt(2));
			xMove= (float) (speed/Math.sqrt(2));
		}else if(handler.getKeyManager().P2up && handler.getKeyManager().P2left) {
			yMove= (float) (-speed/Math.sqrt(2));
			xMove= (float) (-speed/Math.sqrt(2));
		}else if(handler.getKeyManager().P2down && handler.getKeyManager().P2right) {
			yMove= (float) (speed/Math.sqrt(2));
			xMove= (float) (speed/Math.sqrt(2));
		}else if(handler.getKeyManager().P2down && handler.getKeyManager().P2left) {
			yMove= (float) (speed/Math.sqrt(2));
			xMove= (float) (-speed/Math.sqrt(2));
		}else if(handler.getKeyManager().P2up) {
			yMove= -speed;
		} else	if(handler.getKeyManager().P2down) {
			yMove= speed;
		}else if(handler.getKeyManager().P2left) {
			xMove= -speed;
		}else if(handler.getKeyManager().P2right) {
			xMove= speed;
		}
	}
}
