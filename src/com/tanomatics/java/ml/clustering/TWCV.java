package com.tanomatics.java.ml.clustering;

import com.tanomatics.java.ml.clustering.fgka.ga.Chromosom;
import com.tanomatics.java.ml.core.Dataset;


/**
 * A class for generating Total Within Cluster Variance from dataset
 * (See Yi Lu, Shiyong Lu, Farshad Fotouhi, Youping Deng, Susan J. Brown, 
 * <i>Fast Genetic K-means Algorithm and its Application in Gene Expression 
 * Data Analysis</i>)
 * @author yanuwid
 * @version 1.0
 */
public class TWCV {
	private Dataset dataset;
	private int K;
	private int D;
	private double TWCV;
	
	/**
	 * @param dataset A set of data
	 * @param chromosom A set of solution
	 * @param k A number of cluster
	 * @param d dataset dimension 
	 */
	public TWCV(Dataset dataset, Chromosom chromosom, int k, int d){
		this.dataset = (Dataset) dataset.clone();
		this.K = k;
		this.D = d;
		setTWCV(chromosom);
	}
	
	/**
	 * 
	 * @param dataset
	 * @param solution
	 * @param k
	 * @param d
	 */
	public TWCV(Dataset dataset, int[] solution, int k, int d){
		this.dataset = (Dataset) dataset.clone();
		this.K = k;
		this.D = d;
		setTWCV(solution);
	}
	
	/**
	 * set TWCV from chromosom
	 * @param chromosom
	 */
	private void setTWCV(Chromosom chromosom) {
		this.TWCV = evalTWCV(chromosom);
	}
	
	/**
	 * set TWCV from solution
	 * @param solution
	 */
	private void setTWCV(int[] solution) {
		double eZ;
		int n[] = new int[K];
		for(int i = 0; i < dataset.size(); i++)
			n[solution[i]]++;
		
		float e = 0;
		for (int i = 0; i < K; i++)
			if(n[i] !=0 ) e++;
		eZ = e/K;
		
		boolean legality;
		if(eZ == 1) 
			legality = true;
		else 
			legality = false;
		
		this.TWCV = evalTWCV(solution, legality);
	}

	/**
	 * evaluate TWCV from solution
	 * @param solution
	 * @param legality ratio
	 * @return the TWCV
	 */
	private double evalTWCV(int[] solution, boolean legality) {
		double T1		= 0;
		double T2 		= 0;
		int[] z 		= new int[K];
		double[][] sF 	= new double[K][D + 1];
		float X 		= 0;
		
		if (legality){
			//calculate T1;
			for (int i = 0; i < solution.length; i++){
				int k = solution[i];
				z[k]++;
				for(int d = 0; d < D ; d++){
					X 	= dataset.get(i)[d];
					T1 	= T1 + (X * X);
					sF[k][d] = sF[k][d] + X;
				}
			}
			
			// calculate T2
			double t = 0;
			for(int i = 0; i < K; i++) {
				t = 0;
				for (int j = 0; j < D; j++) {
					t = t + (sF[i][j] * sF[i][j]); 
				}
				T2 = T2 + (t / z[i]);
			}
			return T1 - T2;
			
		} else { 
			return Double.POSITIVE_INFINITY;
		}
	}

	/**
	 * Evaluate TWCV from chromosom
	 * @param chromosom
	 * @return the TWCV
	 */
	private double evalTWCV(Chromosom chromosom){
		double T1		= 0;
		double T2 		= 0;
		int[] z 		= new int[K];
		double[][] sF 	= new double[K][D + 1];
		float X 		= 0;
		
		if (chromosom.isLegal()){
			//calculate T1;
			for (int i = 0; i < chromosom.size(); i++){
				int k = chromosom.getGen(i);
				z[k]++;
				for(int d = 0; d < D ; d++){
					X 	= dataset.get(i)[d];
					T1 	= T1 + (X * X);
					sF[k][d] = sF[k][d] + X;
				}
			}
			
			// calculate T2
			double t = 0;
			for(int i = 0; i < K; i++) {
				t = 0;
				for (int j = 0; j < D; j++) {
					t = t + (sF[i][j] * sF[i][j]); 
				}
				T2 = T2 + (t / z[i]);
			}
			return T1 - T2;
			
		} else { 
			return Double.POSITIVE_INFINITY;
		}
	}
	
	/**
	 * @return The TWCV
	 */
	public double getTWCV(){
		return this.TWCV;
	}
	
}
