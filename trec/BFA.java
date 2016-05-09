package org.csci599.trec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class BFA
{
	
	static int[] mArray = new int[Globals.MAX_SIZE];
	static Double[] mNormalizedArray = new Double[Globals.MAX_SIZE];
	static double mMaxValue =0.0;
	static double[] mCompandedArray = new double[Globals.MAX_SIZE];
	
	File f = null;
	String str = null;
	Companding companding = new Companding();
	BFA( File f,String s)
	{
		this.f = f;
		this.str = s;
		for(int i=0;i<Globals.MAX_SIZE;i++)
		{
			mArray[i]=0;
			mNormalizedArray[i]=0.0;
		}	
	}
	
	//Map of type v/s Arraylist of normalized fingerprints is populated in bfaNormalMap 
	public void executeBfa(Map<String,ArrayList<Double[]>> bfaNormalMap,String type) throws IOException
	{
		BFAUtil bfa = new BFAUtil();
		int val = bfa.readContentsOfFile(f,mArray);
		if(val != -1 && val != -2)
		{
			mMaxValue = bfa.setMaxValue(mArray);
			bfa.normalizeArray(mArray,mNormalizedArray,mMaxValue);
			mCompandedArray = companding.returnCompandingArray(mNormalizedArray);
			bfa.populateBFANormalMap(f,bfaNormalMap,type,mNormalizedArray);
		}
	}
}
