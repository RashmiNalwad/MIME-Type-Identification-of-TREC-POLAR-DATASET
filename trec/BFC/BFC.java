package org.csci599.trec.BFC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class BFC
{
	
	static int[] mArray = new int[GlobalsBFC.MAX_SIZE];
	static Double[] mNormalizedArray = new Double[GlobalsBFC.MAX_SIZE];
	static double mMaxValue =0.0;
	static double[] mCompandedArray = new double[GlobalsBFC.MAX_SIZE];
	
	File f = null;
	String str = null;
	Companding companding = new Companding();
	BFC( File f,String s)
	{
		this.f = f;
		this.str = s;
		for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
		{
			mArray[i]=0;
			mNormalizedArray[i]=0.0;
		}	
	}
	public void executeBfc(Map<String,ArrayList<Double[]>> bfaNormalMap,String type) throws IOException
	{
		BFCUtil bfa = new BFCUtil();
		int val = bfa.readContentsOfFile(f,mArray);
		if(val != -1 || val != -2)
		{
			mMaxValue = bfa.setMaxValue(mArray);
			bfa.normalizeArray(mArray,mNormalizedArray,mMaxValue);
			mCompandedArray = companding.returnCompandingArray(mNormalizedArray);
			bfa.populateBFANormalMap(f,bfaNormalMap,type,mNormalizedArray);
		}
	}
}
