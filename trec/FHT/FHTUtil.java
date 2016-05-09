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
import java.util.ArrayList;
import java.util.Map;

import org.csci599.trec.Globals;

public class FHTUtil {

	Combine cfp = new Combine(); 
	
	int readHeader(File f, Integer[][] headerArray, Map<String, ArrayList<Integer[][]>> fhtHeaderMap, String type) throws IOException
	{
		int a = 0;
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
		for(int i=0;i<GlobalsFHT.FHT_HEADER_LENGTH;i++)
		{
			try 
			{
				a = input.read();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
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
	    if(fhtHeaderMap.containsKey(type))
	    {
	    	fhtHeaderMap.get(type).add(headerArray);
	    }
	    else
	    {
	    	fhtHeaderMap.put(type, new ArrayList<Integer[][]>());
	    	fhtHeaderMap.get(type).add(headerArray);
	    	writeToOutput(type,headerArray);
	    }
		input.close();
		return 0;
	}
	
	int readTrailer(File f, Integer[][] trailerArray, Map<String, ArrayList<Integer[][]>> fhtTrailerMap, String type) throws IOException
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
						trailerArray[i][j] = 1;
					else
						trailerArray[i][j] = 0;
				}
			}
			noOfBytes--;
		}
		if(fhtTrailerMap.containsKey(type))
	    {
			fhtTrailerMap.get(type).add(trailerArray);
	    }
	    else
	    {
	    	fhtTrailerMap.put(type, new ArrayList<Integer[][]>());
	    	fhtTrailerMap.get(type).add(trailerArray);
	    }
		raf.close();
		return 0;
	}
	
	private static void writeToOutput(String type,Integer[][] headerArray) throws IOException
	{
		String key = type;
		Integer[][] value = headerArray;
		Path path = Paths.get("/src/Output_HeaderFHT_AllTypes");
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



