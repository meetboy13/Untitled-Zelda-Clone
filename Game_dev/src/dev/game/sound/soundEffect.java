package dev.game.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class soundEffect {
	private Clip clip;
	
	public soundEffect(){
		
	}
	
	public void setFile(String soundFileName) {
		try {
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
}
