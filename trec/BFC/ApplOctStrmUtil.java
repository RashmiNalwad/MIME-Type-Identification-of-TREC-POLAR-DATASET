package org.csci599.trec.BFC;

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

public class ApplOctStrmUtil
{
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
	void normalizeAOSArray(double[] mAOSNormalizedArray, int mAOSMaxValue, int[] mAOSArray)
	{
		double bd=0.0;
		for(int i=0;i<mAOSNormalizedArray.length;i++)
		{
			bd = (double)mAOSArray[i]/mAOSMaxValue;
			mAOSNormalizedArray[i] = bd;
		}
	}
	int readContentsOfAOSFile(File f, int[] mAOSArray) throws IOException
	{
		int a=0;
		String fileName = f.getAbsolutePath();
		System.out.println(fileName);
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
	
	public void compareFingerPrints(double AOSFP[],Map<String, double[]> finalFingerPrintMap,Map<String, Double> ASLMap,String AOSFileName) throws IOException
	{
		double temp[] = new double[GlobalsBFC.MAX_SIZE];
		double tempFinalFP[] = new double[GlobalsBFC.MAX_SIZE];
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
			
			for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
			{
				temp[i]=Math.abs(AOSFP[i] -tempFinalFP[i]);
				if(temp[i] <= Math.abs(tempFinalFP[i] + ASL) && temp[i] >= Math.abs(tempFinalFP[i] - ASL))
				{
					count++;
				}
			}

			// Add path to output file
			String name = AOSFileName + ".txt";
			Path path = Paths.get("/src/results");
			String strFilePath = GlobalsBFC.getPWD() + path.toString() +"/" + name;
			FileWriter fileWritter = new FileWriter(strFilePath,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(keyType+"\t");
	        bufferWritter.write(String.valueOf(count) + "\n");
	        bufferWritter.write("\t\n");
	        bufferWritter.close();
		}
		
	}
	
}

