package org.csci599.trec.FHT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Combine 
{
	/* This method combines 2D fingerPrint values of each byte by using formula 
	 * http://www.forensicswiki.org/w/images/f/f9/Mcdaniel01.pdf Page 95
	 * to a 1D final finger print for each type.
	 *  */
	public void combineFingerPrint(Map<String, ArrayList<Integer[][]>> fhtHeaderMap, Map<String, double[][]> fhtFinalFingerPrintMap) throws IOException
	{
		for(Entry<String, ArrayList<Integer[][]>> entry:fhtHeaderMap.entrySet())
		{
			int prev_num_files =0;
			double[][] fingerPrint = new double[GlobalsFHT.FHT_HEADER_LENGTH][GlobalsFHT.MAX_SIZE];
			String key = entry.getKey();
			ArrayList<Integer[][]> values = entry.getValue();
			for(Integer[][] arr:values)
			{
				for(int i=0;i<GlobalsFHT.FHT_HEADER_LENGTH;i++)
				{
					for(int j=0;j<GlobalsFHT.MAX_SIZE;j++)
					{
						double oldFPScore = fingerPrint[i][j];
						double newFileScore = arr[i][j];
						double newFPScore = ((oldFPScore*prev_num_files) + newFileScore)/(prev_num_files+1);
						fingerPrint[i][j] = newFPScore;
					}
				}
				prev_num_files++;
			}
			fhtFinalFingerPrintMap.put(key, fingerPrint);
		}
	}
}

