package org.csci599.trec.FHT;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class GlobalsFHT {

	public static int MAX_SIZE = 256;
	public static int FHT_HEADER_LENGTH = 4;
	public static int FHT_TRAILER_LENGTH = 4;
	static String inputFile="file1.txt";
	static String tsv=".tsv";
	static String csv=".csv";
	
	//Map of File type and Final Finger Print.
	public static Map<String, double[][]> fhtHeaderFinalFingerPrintMap = new HashMap<String,double[][]>();
	
	//Map of File type and Final Finger Print.
	public static Map<String, double[][]> fhtTrailerFinalFingerPrintMap = new HashMap<String,double[][]>();
	
	public static String getPWD()
	{
		Path path = Paths.get("");
		return path.toAbsolutePath().toString();
	}
	
	public static int getFileCount()
	{
		return (new File(getPWD()).listFiles().length);	   
	}
}
