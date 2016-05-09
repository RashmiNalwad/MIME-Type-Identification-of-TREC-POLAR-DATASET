package org.csci599.trec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

public class ApplOctStrmUtil{

	//Set the max value to use it for normalisation
	int setAOSMaxValue(int[] mAOSArray)
	{
		int maxValue=Integer.MIN_VALUE;
		for(int i=0;i< mAOSArray.length;i++)
		{
			if(maxValue < mAOSArray[i]) 
				maxValue=mAOSArray[i];
		}
		return maxValue;
	}
	
	//Normalise the array by dividing each value in array with maxValue in that array
	void normalizeAOSArray(double[] mAOSNormalizedArray, int mAOSMaxValue, int[] mAOSArray)
	{
		double bd=0.0;
		for(int i=0;i<mAOSNormalizedArray.length;i++)
		{
			if(mAOSMaxValue != 0)
				bd = (double)mAOSArray[i]/mAOSMaxValue;
			mAOSNormalizedArray[i] = bd;
		}
	}
	
	//Read entire file and compute byte frequencies and populate results in mAOSArray
	int readContentsOfAOSFile(File f, int[] mAOSArray) throws IOException
	{
		int a=0;
		long longlength = f.length();
		int length = (int) longlength;
		if(length > Integer.MAX_VALUE)
		{
			System.out.println("File exceeding maximum size");
			System.out.println(f.getAbsolutePath() + f.getName());
			return -1;
		}
		if( length == 0 )
		{
			System.out.println("File length 0");
			System.out.println(f.getAbsolutePath() + f.getName());
			return -2;
		}
		InputStream input = new FileInputStream(f);
		while (a != -1) {
			try 
			{
				a = input.read();
				if(a == -1)
					break;
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			mAOSArray[a]=mAOSArray[a]+1;
		}
		input.close();
		return 0;
	}
	
	//Compare finger print of unknown file with the known files fingerprint and find the potentiall match
	public void compareFingerPrints(double AOSFP[],Map<String, double[]> finalFingerPrintMap,Map<String, Double> ASLMap,String AOSFileName) throws IOException
	{
		double tempFinalFP[] = new double[Globals.MAX_SIZE];
		String keyType = new String();
		double ASL = 0.0;
		int count =0;
		
		File file =new File(AOSFileName+".txt");
		if(!file.exists()){
			file.createNewFile();
		}
		
		for( Entry<String, double[]> entry : finalFingerPrintMap.entrySet())
		{
			keyType=entry.getKey();
			tempFinalFP = entry.getValue();
			ASL = ASLMap.get(keyType);
			count=0;
			
			for(int i=0;i<Globals.MAX_SIZE;i++)
			{
				if((AOSFP[i] >= tempFinalFP[i]-0.01) && (AOSFP[i] <= tempFinalFP[i]+0.01) )
				{
					count++;
				}
			}
			// Add path to output file
			String name = AOSFileName + ".txt";
			Path path = Paths.get("/src/results");
			String strFilePath = Globals.getPWD() + path.toString() +"/" + name;
			FileWriter fileWritter = new FileWriter(strFilePath,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(keyType+"\t\t" + String.valueOf(count) + "\n");
	        bufferWritter.write("\t\n");
	        bufferWritter.close();
		}
	}
	
}

