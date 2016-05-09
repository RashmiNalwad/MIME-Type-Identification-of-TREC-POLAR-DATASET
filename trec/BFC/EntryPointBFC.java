package org.csci599.trec.BFC;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tika.Tika;
import org.csci599.trec.Globals;

public class EntryPointBFC {
	
	// Map having file type as Key and its normalized array as value.
	static Map<String,ArrayList<Double[]>> bfcNormalMap = new HashMap<String,ArrayList<Double[]>>();

	public static void main(String[] args) throws IOException
	{
//		Path path = Paths.get("/src/input");/Users/Shashank/Downloads/CSCI-599_DATA/TREC
//		File dir = new File(Globals.getPWD() + path.toString());
		Path path = Paths.get("src/input/input");
		File dir = new File(path.toString());
		File[] files = dir.listFiles();
		String type=null;
		
		for (File f : files)
		{
			if(f.isFile())
			{
				Tika tika =  new Tika();
				type=tika.detect(f);
				String currentLocation=f.toString();
				String newLocation="D:\\TREC\\octet_stream\\"+f.getName();
				File _file=new File(currentLocation);
				if(type.equalsIgnoreCase("application/octet-stream"))
				{
					_file.renameTo(new File(newLocation));
				}
				else
				{
					BFC bfc = new BFC(f,type);
					bfc.executeBfc(bfcNormalMap,type);	
				}
			}
		}
		//Class that has methods to combine normalised arrays of all file to generate a FingerPrint
		Combine cFP = new Combine(); 
		cFP.combineFingerPrint(bfcNormalMap,GlobalsBFC.bfaFingerPrintMap);
		GlobalsBFC.mFP=GlobalsBFC.bfaFingerPrintMap.get(type);
		
		Corelation co = new Corelation();
		co.computeCorelationFactor(bfcNormalMap,GlobalsBFC.bfaFingerPrintMap);
		
		cFP.combineCorelation(GlobalsBFC.bfaCoFactorMap, GlobalsBFC.bfaCoRelationStrengthMap);
		
		String key = "";
		for(Entry<String, double[]> entry:GlobalsBFC.bfaFingerPrintMap.entrySet())
		{
			key = entry.getKey();
			GlobalsBFC.mFP = entry.getValue();
			GlobalsBFC.mCS=GlobalsBFC.bfaCoRelationStrengthMap.get(key);
			double[][] mBFCMatrix = new double[GlobalsBFC.MAX_SIZE][GlobalsBFC.MAX_SIZE];
			Path pat = Paths.get("/src/outputBFC");
			String strFilePath = Globals.getPWD() + pat.toString() +"\\" + key.split("/")[1] +"_" +GlobalsBFC.csv;
			FileWriter fileWriter = new FileWriter(strFilePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("source,target,weight");
			bufferedWriter.newLine();
			
			for(int i=0;i<255;i++)
			{
				for(int j=0;j<255;j++)
				{
					if(i>=j)
					{
						mBFCMatrix[i][j]=GlobalsBFC.mFP[i] - GlobalsBFC.mFP[j];
					}
					else
					{
						mBFCMatrix[i][j]= GlobalsBFC.mCS[i]-GlobalsBFC.mCS[j];
					}
					bufferedWriter.write(i+","+j+","+mBFCMatrix[i][j]);
					bufferedWriter.newLine();
				}
			}
			GlobalsBFC.bfcFinalFingerPrintMap.put(key, mBFCMatrix);
			bufferedWriter.close();
		}
		
		Combine.computeAssuranceLevel(GlobalsBFC.bfaCoRelationStrengthMap);//test
		System.out.println("Done -- Computed Final Finger Print and Co-relation strength");
		
		Path AOSpath = Paths.get("D:\\TREC\\octet_stream");
		File AOSdir = new File(AOSpath.toString());
		File[] AOSfiles = AOSdir.listFiles();

		for (File AOSf : AOSfiles)
		{
			if(AOSf.isFile())
			{
				ApplOctStrm AOSOBJ = new ApplOctStrm(AOSf);
				AOSOBJ.executeAOS();
			}
		}
	}
	
}	
	
