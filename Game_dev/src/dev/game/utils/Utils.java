package dev.game.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utils {
	public static String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) !=null) {
				builder.append(line +"\n");
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	public static void saveFileAsString(String path,String data)
				  throws IOException {
				    FileWriter fileWriter = new FileWriter(path);
				    PrintWriter printWriter = new PrintWriter(fileWriter);
				    printWriter.print(data);
				    printWriter.close();
	}
	
	public static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
		}
}
