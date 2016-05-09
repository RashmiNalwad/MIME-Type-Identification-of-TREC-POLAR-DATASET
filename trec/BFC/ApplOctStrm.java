package org.csci599.trec.BFC;

import java.io.File;
import java.io.IOException;

public class ApplOctStrm {

	File f = null;
	
	Companding companding = new Companding();
	static double[] mAOSDifferenceFPArray = new double[GlobalsBFC.MAX_SIZE];
	static double[] mAOSDifferenceCORArray = new double[GlobalsBFC.MAX_SIZE];
	static int[] mAOSArray = new int[GlobalsBFC.MAX_SIZE];
	static double[] mAOSNormalizedArray = new double[GlobalsBFC.MAX_SIZE];
	
	static int mAOSMaxValue =0;
	static double[] mAOSCompandedArray = new double[GlobalsBFC.MAX_SIZE];
	public ApplOctStrm(File f)
	{
		this.f = f;
		for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
		{
			mAOSArray[i]=0;
			mAOSNormalizedArray[i]=0.0;
			mAOSDifferenceFPArray[i]=0.0;
			mAOSDifferenceCORArray[i]=0.0;
		}	
	}
	
	public void executeAOS() throws IOException
	{
		ApplOctStrmUtil AOSUtil = new ApplOctStrmUtil();
		int val = AOSUtil.readContentsOfAOSFile(f,mAOSArray);
		if(val != -1 || val != -2)
		{
			mAOSMaxValue = AOSUtil.setAOSMaxValue(mAOSArray);
			AOSUtil.normalizeAOSArray(mAOSNormalizedArray,mAOSMaxValue,mAOSArray);
			AOSUtil.compareFingerPrints(mAOSNormalizedArray, GlobalsBFC.bfaFingerPrintMap, GlobalsBFC.bfaAssuranceLevelMap, f.getName());
		}
	}
	
	
}
