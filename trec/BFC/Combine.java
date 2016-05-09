package org.csci599.trec.BFC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Combine 
{
	/* This method combines fingerPrint values of each byte by using formula 
	 * http://www.forensicswiki.org/w/images/f/f9/Mcdaniel01.pdf Page 95 */
	public void combineFingerPrint(Map<String,ArrayList<Double[]>> normalMap, Map<String, double[]> fingerPrintMap) throws IOException
	{
		for(Entry<String,ArrayList<Double[]>> entry:normalMap.entrySet())
		{
			int prev_num_files =0;
			double[] fingerPrint = new double[GlobalsBFC.MAX_SIZE];
			String key = entry.getKey();
			ArrayList<Double[]> values = entry.getValue();
			for(Double[] arr:values)
			{
				for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
				{
					double oldFPScore = fingerPrint[i];
					double newFileScore = arr[i];
					double newFPScore = ((oldFPScore*prev_num_files) + newFileScore)/(prev_num_files+1);
					fingerPrint[i] = newFPScore;
				}
				prev_num_files++;
			}
			fingerPrintMap.put(key, fingerPrint);
		}
	}
	
	public void combineCorelation(Map<String, ArrayList<double[]>> coFactorMap, Map<String, double[]> coRelationStrengthMap) throws IOException
	{
		for(Entry<String, ArrayList<double[]>> entry:coFactorMap.entrySet())
		{
			int prev_num_files =0;
			double[] coRelationStrength = new double[GlobalsBFC.MAX_SIZE];
			for(int i = 0;i<GlobalsBFC.MAX_SIZE;i++)
			{
				coRelationStrength[i] = 1.0;
			}
			String key = entry.getKey();
			ArrayList<double[]> values = entry.getValue();
			for(double[] arr:values)
			{
				for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
				{
					double oldFPScore = coRelationStrength[i];
					double newFileScore = arr[i];
					double newFPScore = ((oldFPScore*prev_num_files) + newFileScore)/(prev_num_files+1);
					coRelationStrength[i] = newFPScore;
				}
				prev_num_files++;
			}
			coRelationStrengthMap.put(key, coRelationStrength);
		}
	}
	
	public static void computeAssuranceLevel(Map<String, double[]> bfaCoRelationStrengthMap)
	{
		for(Entry<String, double[]> entry:bfaCoRelationStrengthMap.entrySet())
		{
			String key = entry.getKey();
			double[] value = entry.getValue();
			double sum = 0.0;
			double avg = 0.0;
			for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
			{
				sum = sum + value[i];
			}
			avg = sum/256;
			GlobalsBFC.bfaAssuranceLevelMap.put(key, avg);
		}
	}
}

