package org.csci599.trec;

public class Companding {

	int beta;

	Companding()
	{
		beta = 2;
	}
	public double[] returnCompandingArray(Double[] mNormalizedArray)
	{
		double [] mThisArrayToReturn= new double[Globals.MAX_SIZE];
		for(int i=0;i<mNormalizedArray.length;i++)
		{
			//mThisArrayToReturn[i] = Math.pow(mNormalizedArray[i], (1/beta));
			mThisArrayToReturn[i] = mNormalizedArray[i] * 10;
		}
		return mThisArrayToReturn;
	}
	public void setBeta(int beta)
	{
		this.beta=beta;
	}
	public int getBeta()
	{
		return beta;
	}
}

