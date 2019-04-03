import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main {
	public static void main(String args[]){
		print("Small beginings");
		print("Its working!");
		JFrame temp = new JFrame();
		temp.setSize(500, 500);
		temp.setVisible(true);
		temp.setTitle("Game window");
		temp.setResizable(false);
		////
		ImageIcon image=null;
		image = new ImageIcon("/Textures/background.jpg");
	    JLabel lable = new JLabel(image);
	    JScrollPane jsp = new JScrollPane(lable);
	    temp.getContentPane().add(jsp);
	    //////
	    
	    
	}
	public static void print(String input) {
		System.out.println(input);
	}
}

