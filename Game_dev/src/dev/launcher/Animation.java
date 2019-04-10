package dev.launcher;

import java.awt.image.BufferedImage;

public class Animation {
	private int speed,index;
	private BufferedImage[] frames;
	long lastTime;
	long timer;
	//constructor
	public Animation(int speed, BufferedImage[] frames) {
		this.speed=speed;
		this.frames=frames;
		index=0;
		timer=0;
		lastTime=System.currentTimeMillis();
	}
	//tick through animation cycle
	public void tick() {
		timer+=System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if (timer>speed) {
			index++;
			timer=0;
			if (index>=frames.length) {
				index=0;
			}
		}
	}
	//getter
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}
}
