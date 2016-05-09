package org.csci599.trec.FHT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FHT
{
	static Integer[][] headerArray = new Integer[GlobalsFHT.FHT_HEADER_LENGTH][GlobalsFHT.MAX_SIZE];
	
	static Integer[][] trailerArray = new Integer[GlobalsFHT.FHT_TRAILER_LENGTH][GlobalsFHT.MAX_SIZE];
	
	File f = null;
	String str = null;
	FHT( File f,String s)
	{
		this.f = f;
		this.str = s;
		for(int i=0;i<GlobalsFHT.FHT_HEADER_LENGTH;i++)
		{
			for(int j =0;j<GlobalsFHT.MAX_SIZE;j++)
				headerArray[i][j] = -1;
		}
		
		for(int i=0;i<GlobalsFHT.FHT_TRAILER_LENGTH;i++)
		{
			for(int j =0;j<GlobalsFHT.MAX_SIZE;j++)
				trailerArray[i][j] = -1;
		}
	}
	public void executeFHT(Map<String,ArrayList<Integer[][]>> fhtHeaderMap,Map<String, ArrayList<Integer[][]>> fhtTrailerMap, String type) throws IOException
	{
		FHTUtil fht = new FHTUtil();
		fht.readHeader(f,headerArray,fhtHeaderMap,type); //populates Header Map.
		fht.readTrailer(f,trailerArray,fhtTrailerMap,type); //populates Trailer Map. 
	}
}
