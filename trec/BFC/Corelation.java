package org.csci599.trec.BFC;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Corelation 
{
	/* This method computes co-relation factor for each byte using formula :
	 * 
	 * 
	 * 
	 */
	
	
    public void computeCorelationFactor(Map<String, ArrayList<Double[]>> normalMap, Map<String, double[]> finalFingerPrintMap)
    {
    	for(Entry<String,ArrayList<Double[]>> entry:normalMap.entrySet())
		{
    		String key = entry.getKey();
			ArrayList<Double[]> values = entry.getValue();
			double[] fFPMap = finalFingerPrintMap.get(key);
			for(Double[] arr:values)
			{
				double[] coFactor = new double[GlobalsBFC.MAX_SIZE];
				double[] diffArray = new double[GlobalsBFC.MAX_SIZE];
				for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
				{
					double val = Math.abs(fFPMap[i] - arr[i]);
					diffArray[i] = val; // values after subtracting final FP with each NV. // X
				}
				calcualateCoFactor(diffArray,coFactor);
				if(GlobalsBFC.bfaCoFactorMap.containsKey(key))
				{
					GlobalsBFC.bfaCoFactorMap.get(key).add(coFactor);                
				}
				else
				{
					GlobalsBFC.bfaCoFactorMap.put(key, new ArrayList<double[]>());
					GlobalsBFC.bfaCoFactorMap.get(key).add(coFactor);
				}
			}	
		}
    }
    
    public void calcualateCoFactor(double[] diffArray, double[] coFactor)
	{
		double SD = 0.0;
		double Variance = 0.0;
		double raisedToPower=0.0;

		DescriptiveStatistics stats = new DescriptiveStatistics();

		for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
		{
			//Calculate all the required statistics
			stats.addValue(diffArray[i]);
		}
		
		SD=stats.getStandardDeviation();
		Variance = SD * SD;
		
		for(int i=0;i<GlobalsBFC.MAX_SIZE;i++)
		{
			raisedToPower = (diffArray[i] * diffArray[i]) * (-1);
			if(Variance != 0.0)
				raisedToPower = raisedToPower / Variance;
			coFactor[i]=Math.exp(raisedToPower);
		}
	}
}
