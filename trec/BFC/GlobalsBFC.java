package org.csci599.trec.BFC;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GlobalsBFC {

	public static int MAX_SIZE = 256;
	static String tsv=".tsv";
	static String csv=".csv";
	static double[] mFP = new double[GlobalsBFC.MAX_SIZE];
	static double[] mCS = new double[GlobalsBFC.MAX_SIZE];
	
	
	//Map of File type and Final Finger Print.
	public static Map<String, double[]> bfaFingerPrintMap = new HashMap<String,double[]>();
	
	//Map of File type and Co-relation strength every file
	public static Map<String, ArrayList<double[]>> bfaCoFactorMap = new HashMap<String,ArrayList<double[]>>();
	
	//Map of File type and Corelation strength Value. Final Value
    public static Map<String, double[]> bfaCoRelationStrengthMap = new HashMap<String,double[]>();
    
  //Map of File type and Assurance level for each file type
    public static Map<String, Double> bfaAssuranceLevelMap = new HashMap<String,Double>();
    
    public static Map<String,double[][]> bfcFinalFingerPrintMap = new HashMap<String, double[][]>();
	
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
