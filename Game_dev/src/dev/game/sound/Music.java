package dev.game.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	private static Clip clip;
	
	public Music() {
		
	}
	
	public void setFile(String soundFileName) {
		try {
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
		clip.close();
	}
	public static synchronized void playSound(final String soundFileName) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		    	//run the sound file an loop it
				File file = new File(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
		        clip = AudioSystem.getClip();
		        clip.open(sound);
		        clip.loop(Clip.LOOP_CONTINUOUSLY);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
}
