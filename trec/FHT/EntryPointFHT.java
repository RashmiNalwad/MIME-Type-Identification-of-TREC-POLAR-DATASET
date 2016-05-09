package org.csci599.trec.FHT;

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

public class EntryPointFHT {
	
	// Map having file type as Key and its normalized array as value. Header
	static Map<String,ArrayList<Integer[][]>> fhtHeaderMap = new HashMap<String,ArrayList<Integer[][]>>();

	// Map having file type as Key and its normalized array as value. Trailer
	static Map<String,ArrayList<Integer[][]>> fhtTrailerMap = new HashMap<String,ArrayList<Integer[][]>>();
	
	public static void main(String[] args) throws IOException
	{
		Path path = Paths.get("/src/input/input");
		File dir = new File(GlobalsFHT.getPWD() + path.toString());
//		
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
				String newLocation="D:\\TREC\\octet_stream_FHT\\"+f.getName();
				File _file=new File(currentLocation);
				if(type.equalsIgnoreCase("application/octet-stream")) //Application/Octet stream files are moved to newLocation
				{
					_file.renameTo(new File(newLocation));
				}
				else
				{
					FHT fht = new FHT(f,type);
					fht.executeFHT(fhtHeaderMap,fhtTrailerMap,type); //FHTHeader and TrailerMap gets populated	
				}
			}
		}
		Combine cFP = new Combine(); 
		cFP.combineFingerPrint(fhtHeaderMap,GlobalsFHT.fhtHeaderFinalFingerPrintMap); //Header fingerprints for each MIMEtype is combined to get final fingerprint for each type.
		cFP.combineFingerPrint(fhtTrailerMap,GlobalsFHT.fhtTrailerFinalFingerPrintMap); //Trailer fingerprints for each MIMEtype is combined to get final fingerprint for each type.
		writeToOutput(GlobalsFHT.fhtHeaderFinalFingerPrintMap); // Writing results to file for D3 Visualization.
		writeToOutput(GlobalsFHT.fhtTrailerFinalFingerPrintMap); // Writing results to file for D3 Visualization.
		
		// Write to output to create D3 visualisation for each type of file.
		
		// Finding the result and new fingerprint identified should be added to fingerprint map.
		Path AOSpath = Paths.get("D:\\TREC\\octet_stream_FHT");
		File AOSdir = new File(AOSpath.toString());
		File[] AOSfiles = AOSdir.listFiles();

		for (File AOSf : AOSfiles)
		{
			if(AOSf.isFile())
			{
				ApplOctStrm AOSOBJ = new ApplOctStrm(AOSf);
				AOSOBJ.executeAOS(); // Computing fingerprint for application/octet stream files.
			}
		}
   }
	
	// Writing results to file for D3 Visualization.
	private static void writeToOutput(Map<String, double[][]> fhtFinalFingerPrintMap) throws IOException
	{
		for(Entry<String, double[][]> entry:fhtFinalFingerPrintMap.entrySet())
		{
			String key = entry.getKey();
			double[][] value = entry.getValue();
			Path path = Paths.get("/src/output_Trailer_FHT");
			String name = "";
			if(key.contains("/"))
			{
				name = key.split("/")[1];
			}
			else
			{
				name = key;
			}
			String strFilePath = Globals.getPWD() + path.toString() +"\\" + name + GlobalsFHT.csv;
			FileWriter fileWriter = new FileWriter(strFilePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("type,i,j,weight\n");
			for(int i=0;i<value.length;i++)
			{
				for(int j=0;j<value[0].length;j++)
				{
					if(value[i][j] > 0)
					{
						bufferedWriter.write(key+","+i+","+j+","+value[i][j]);
						bufferedWriter.write("\n");
					}
				}
			}
			bufferedWriter.close();
		}
	}	
}	
	
