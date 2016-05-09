package org.csci599.trec.BFC;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bytes {
	public static void main(String args[]) throws IOException{
		int myArray[] = new int[256];
		Path path = Paths.get("/src/input");
		File dir = new File(GlobalsBFC.getPWD() + path.toString());
		File[] files = dir.listFiles();
		String fileName = files[0].getAbsolutePath();
		System.out.println(fileName);
		InputStream input = new FileInputStream(files[0]);
		int a = 0;
		while (a != -1) {
			try {
				a = input.read();
				if(a==-1) break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myArray[a]=myArray[a]+1;
		}
		input.close();
		}
	}
