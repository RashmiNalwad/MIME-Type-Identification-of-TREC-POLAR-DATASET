package org.csci599.trec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.Tika;

public class EntryPoint {

	// Map having file type as Key and its normalized array as value.
	static Map<String,ArrayList<Double[]>> bfaNormalMap = new HashMap<String,ArrayList<Double[]>>();

	public static void main(String[] args) throws IOException
	{
		Path path = Paths.get("/src/input");
		File dir = new File(Globals.getPWD() + path.toString());

		//		Path path = Paths.get("D:\\TREC\\ch_cl_club");
		//		File dir = new File(path.toString());

		File[] files = dir.listFiles();

		for (File f : files)
		{
			if(f.isFile())
			{
				Tika tika =  new Tika();
				String type=tika.detect(f);
				String currentLocation=f.toString();
				String newLocation="D:\\TREC\\octet_stream\\"+f.getName();
				File _file=new File(currentLocation);
				if(type.equalsIgnoreCase("application/octet-stream"))  //Application/Octet stream files are moved to newLocation
				{
					_file.renameTo(new File(newLocation));
				}
				else
				{
					BFA bfa = new BFA(f,type);
					bfa.executeBfa(bfaNormalMap,type);	//Map of type v/s Arraylist of normalized fingerprints is populated in bfaNormalMap. 
				}
			}
		}
		//Class that has methods to combine normalised arrays of all file to generate a FingerPrint
		Combine cFP = new Combine(); 
		cFP.combineFingerPrint(bfaNormalMap,Globals.bfaFinalFingerPrintMap); //Fingerprint of each MIME types are combined to obtain final finger print for each MIME Type.

		Corelation co = new Corelation();
		co.computeCorelationFactor(bfaNormalMap,Globals.bfaFinalFingerPrintMap); // Populating Cofactor map.

		cFP.combineCorelation(Globals.bfaCoFactorMap, Globals.bfaCoRelationStrengthMap); // Combing cofactor values for each MIME type to get final correlation strength for each MIME type.

		Combine.computeAssuranceLevel(Globals.bfaCoRelationStrengthMap);//Compute Assurance level for each type.

		System.out.println("Done -- Computed Final Finger Print and Co-relation strength");

		//Writing Final finger print and Correlation to output for D3 visualisation.
		writeFinalOutput( Globals.bfaFinalFingerPrintMap,Globals.bfaCoRelationStrengthMap); 

		// Following code Runs BFA on unknown Files
		Path AOSpath = Paths.get("D:\\TREC\\octet_stream");
		File AOSdir = new File(AOSpath.toString());
		File[] AOSfiles = AOSdir.listFiles();

		for (File AOSf : AOSfiles)
		{
			if(AOSf.isFile())
			{
				ApplOctStrm AOSOBJ = new ApplOctStrm(AOSf);
				AOSOBJ.executeAOS(); // Computing fingerprint for unknown file
			}
		}
	}

	//Writing BFA output for D3 visualisation
	private static void writeFinalOutput(Map<String, double[]> bfaFinalFingerPrintMap, Map<String, double[]> bfaCoRelationStrengthMap) throws IOException
	{
		Path pat = Paths.get("/src/output");
		String strFilePath = Globals.getPWD() + pat.toString() +"/" + "BFAOutput" + "_"+ Globals.csv;
		FileWriter fileWriter = new FileWriter(strFilePath);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write("Type,Frequency,FingerPrint,Corelation");
		bufferedWriter.newLine();

		int i=0;
		int j=i+1;
		while(i<256)
		{
			for(String key:bfaFinalFingerPrintMap.keySet())
			{
				StringBuilder sb = new StringBuilder();
				sb.append(key+","+j+","+bfaFinalFingerPrintMap.get(key)[i]+","+bfaCoRelationStrengthMap.get(key)[i]);
				bufferedWriter.write(sb.toString());
				bufferedWriter.newLine();
			}
			i++;
			j = i+1;
		}
		bufferedWriter.close();
	}
}	

