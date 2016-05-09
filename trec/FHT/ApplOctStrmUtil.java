package org.csci599.trec.FHT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import org.csci599.trec.Globals;

public class ApplOctStrmUtil
{

	public int readHeader(File f, Integer[][] headerArray) throws IOException 
	{
		int a=GlobalsFHT.FHT_HEADER_LENGTH;
		int noOfBytes = GlobalsFHT.FHT_HEADER_LENGTH;
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
		while (noOfBytes != 0) {
			try 
			{
				a = input.read();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			for(int i=0;i<GlobalsFHT.FHT_HEADER_LENGTH;i++)
			{
				for(int j=0;j<GlobalsFHT.MAX_SIZE;j++)
				{
					if( a == j)
					{
						headerArray[i][j] = 1;
					}
					else
						headerArray[i][j] = 0;
				}
			}
			noOfBytes--;
		}
		input.close();
		return 0;
	}

	public int readTrailer(File f, Integer[][] unFileTrailerArray) throws IOException
	{
		int a = 0;
		int noOfBytes = GlobalsFHT.FHT_TRAILER_LENGTH;
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
		RandomAccessFile raf = new RandomAccessFile(f,"r");
		raf.seek(f.length()-GlobalsFHT.FHT_TRAILER_LENGTH);
		while(noOfBytes != 0)
		{
			try 
			{
				a = raf.readByte();
				
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			for(int i=0;i<GlobalsFHT.FHT_TRAILER_LENGTH;i++)
			{
				for(int j=0;j<GlobalsFHT.MAX_SIZE;j++)
				{
					if( a == j)
						unFileTrailerArray[i][j] = 1;
					else
						unFileTrailerArray[i][j] = 0;
				}
			}
			noOfBytes--;
		}
		raf.close();
		return 0;
	}

	//Comparing headerArray of unknown file with all fingerprints to compute score and check if its potential match to onw of the known types.
	public void compareToFinalFingerPrint(File f,Integer[][] unFileHeaderArray, Integer[][] unFileTrailerArray,Map<String, double[][]> fhtHeaderFinalFingerPrintMap,Map<String, double[][]> fhtTrailerFinalFingerPrintMap) throws IOException
	{
		Path path = Paths.get("/src/output_Header_FHT");
		String strFilePath = Globals.getPWD() + path.toString() +"/" + f.getName() + "_"+ GlobalsFHT.csv;
		FileWriter fileWriter = new FileWriter(strFilePath);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		// For Header
		for(Entry<String,double[][]> entry:fhtHeaderFinalFingerPrintMap.entrySet())
		{
			String key = entry.getKey();
			double[][] values = entry.getValue();
			double score = 0;
			double deno = 0;
			double c = 0;
			double g = 0;
			for(int i=0;i<GlobalsFHT.FHT_HEADER_LENGTH;i++)
			{
				for(int j=0;j<Globals.MAX_SIZE;j++)
				{
					if(unFileHeaderArray[i][j] == 1)
					{
						c = unFileHeaderArray[i][j];
						g = values[i][j];
						score += (c*g);
						deno += g;
					}
				}
			}
			score = score/deno;
			bufferedWriter.write(key + "\t" + score);
		}
		bufferedWriter.close();
		
		Path path1 = Paths.get("/src/output_Trailer_FHT");
		String strFilePath1 = Globals.getPWD() + path1.toString() +"/" + f.getName() + "_"+ GlobalsFHT.csv;
		FileWriter fileWriter1 = new FileWriter(strFilePath1);
		BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
		
		// For Trailer 
		for(Entry<String,double[][]> entry:fhtTrailerFinalFingerPrintMap.entrySet())
		{
			String key = entry.getKey();
			double[][] values = entry.getValue();
			double score = 0;
			double deno = 0;
			double c = 0;
			double g = 0;
			for(int i=0;i<GlobalsFHT.FHT_TRAILER_LENGTH;i++)
			{
				for(int j=0;j<Globals.MAX_SIZE;j++)
				{
					if(unFileTrailerArray[i][j] == 1)
					{
						c = unFileTrailerArray[i][j];
						g = values[i][j];
						score += (c*g);
						deno += g;
					}
				}
			}
			score = score/deno;
			bufferedWriter1.write(key + "\t" + score);
		}
		bufferedWriter1.close();
	}
}

