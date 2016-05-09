package org.csci599.trec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class BFAUtil {

	Combine cfp = new Combine(); 

	int setMaxValue(int[] mArray)
	{
		int maxValue=Integer.MIN_VALUE;
		for(int i=0;i< mArray.length;i++)
		{
			if(maxValue < mArray[i]) 
				maxValue=mArray[i];
		}
		return maxValue;
	}
	
	//Normalise the array by dividing each value by maxValue in the array.
	void normalizeArray(int[] mArray, Double[] mNormalizedArray, double mMaxValue)
	{
		double bd=0.0;
		for(int i=0;i<mNormalizedArray.length;i++)
		{
			bd = (double)mArray[i]/mMaxValue;
			mNormalizedArray[i] = bd;
		}
	}

	//Writing to normal Map and to out.tsv file.Out.tsv required for d3
	void populateBFANormalMap(File f,Map<String,ArrayList<Double[]>> bfaNormalMap,String type, Double[] mNormalizedArray) throws IOException
	{
		if(bfaNormalMap.containsKey(type)) // Writes to Global NormalisedMap.
		{
			bfaNormalMap.get(type).add(mNormalizedArray);                
		}
		else
		{
			bfaNormalMap.put(type, new ArrayList<Double[]>());
			bfaNormalMap.get(type).add(mNormalizedArray);
		}
	}
	
	//readContents of file and populate mArray with Byte frequencies.
	int readContentsOfFile(File f, int[] mArray) throws IOException
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
			mArray[a]=mArray[a]+1;
		}
		input.close();
		return 0;
	}
	
}



