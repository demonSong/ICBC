package com.demon.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileHelper {
	
	public static boolean fileExist(String fileName){
		File f = new File(fileName);
		return f.exists() && f.isFile();
	}
	
	public static void clearFile(String fileName){
		File f = new File(fileName);
		if (!fileExist(fileName)) return;
		try {
			FileWriter fileWriter = new FileWriter(f);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
