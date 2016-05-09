package org.csci599.trec;

import java.io.File;
import java.io.IOException;

public class ApplOctStrm {

	File f = null;
	
	Companding companding = new Companding();
	static double[] mAOSDifferenceFPArray = new double[Globals.MAX_SIZE];
	static double[] mAOSDifferenceCORArray = new double[Globals.MAX_SIZE];
	static int[] mAOSArray = new int[Globals.MAX_SIZE];
	static double[] mAOSNormalizedArray = new double[Globals.MAX_SIZE];
	
	static int mAOSMaxValue =0;
	static double[] mAOSCompandedArray = new double[Globals.MAX_SIZE];
	public ApplOctStrm(File f)
	{
		this.f = f;
		for(int i=0;i<Globals.MAX_SIZE;i++)
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
		int val = AOSUtil.readContentsOfAOSFile(f,mAOSArray); // Computing fingerprint for unkown file. 
		if(val != -1 && val != -2)//If file is not empty normalize the value and compare to fingerprints obtained to find potential match
		{
			mAOSMaxValue = AOSUtil.setAOSMaxValue(mAOSArray);
			AOSUtil.normalizeAOSArray(mAOSNormalizedArray,mAOSMaxValue,mAOSArray);
			AOSUtil.compareFingerPrints(mAOSNormalizedArray, Globals.bfaFinalFingerPrintMap, Globals.bfaAssuranceLevelMap, f.getName());
		}
		else //Move empty files to different folder
		{
			String currentLocation=f.toString();
			String newLocation="D:\\TREC\\empty_octet_stream\\"+f.getName();
			File _file=new File(currentLocation);
			_file.renameTo(new File(newLocation));
		}
			
	}
	
	
}
