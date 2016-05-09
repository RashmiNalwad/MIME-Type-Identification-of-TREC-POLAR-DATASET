package org.csci599.trec.FHT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.csci599.trec.Globals;

public class ApplOctStrm {

	File f = null;
    static Integer[][] UnFileHeaderArray = new Integer[GlobalsFHT.FHT_HEADER_LENGTH][GlobalsFHT.MAX_SIZE];
	static Integer[][] UnFileTrailerArray = new Integer[GlobalsFHT.FHT_TRAILER_LENGTH][GlobalsFHT.MAX_SIZE];
	public ApplOctStrm(File f)
	{
		this.f = f;
		for(int i=0;i<GlobalsFHT.FHT_HEADER_LENGTH;i++)
		{
			for(int j =0;j<GlobalsFHT.MAX_SIZE;j++)
				UnFileHeaderArray[i][j] = -1;
		}
		
		for(int i=0;i<GlobalsFHT.FHT_TRAILER_LENGTH;i++)
		{
			for(int j =0;j<GlobalsFHT.MAX_SIZE;j++)
				UnFileTrailerArray[i][j] = -1;
		}
	}
	
	public void executeAOS() throws IOException
	{
		ApplOctStrmUtil AOSUtil = new ApplOctStrmUtil();
		AOSUtil.readHeader(f,UnFileHeaderArray); //populates Header Map.
		AOSUtil.readTrailer(f,UnFileTrailerArray); //populates Trailer Map. 
//		writeToOutput(UnFileHeaderArray);
//		writeToOutput(UnFileTrailerArray);
		
		//Comparing headerArray of unknown file with all fingerprints to compute score and check if its potential match to onw of the known types.
		AOSUtil.compareToFinalFingerPrint(f,UnFileHeaderArray,UnFileTrailerArray,GlobalsFHT.fhtHeaderFinalFingerPrintMap,GlobalsFHT.fhtTrailerFinalFingerPrintMap);
	}

	private void writeToOutput(Integer[][] unKnownFile) throws IOException
	{
		Path path = Paths.get("/src/output_Header_FHT");
		String strFilePath = Globals.getPWD() + path.toString() +"\\" + f.getName() + GlobalsFHT.csv;
		FileWriter fileWriter = new FileWriter(strFilePath);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write("type,i,j,weight\n");
		for(int i=0;i<unKnownFile.length;i++)
        {
        	for(int j=0;j<unKnownFile[0].length;j++)
        	{
        		if(unKnownFile[i][j] > 0)
        		{
        			bufferedWriter.write(i+","+j+","+unKnownFile[i][j]);
        			bufferedWriter.write("\n");
        		}
        	}
        }
		bufferedWriter.close();
	}
	
	
}
