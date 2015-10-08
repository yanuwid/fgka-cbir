package com.tanomatics.java.ml.clustering;

import java.util.logging.Logger;

import com.tanomatics.java.ml.clustering.fgka.ga.Chromosom;
import com.tanomatics.java.ml.core.Dataset;



/**
 * A Class for generating centroids from dataset
 * (See Yi Lu, Shiyong Lu, Farshad Fotouhi, Youping Deng, Susan J. Brown, 
 * <i>Fast Genetic K-means Algorithm and its Application in Gene Expression 
 * Data Analysis</i>)
 * @author yanuwid
 * @version 1.0
 */
public class Centroids {
	
	static Logger logger = Logger.getLogger("Centroid.class");
	
	/**
	 * A set of dataset 
	 */
	private Dataset dataset;
	
	/**
	 * Number of cluster
	 */
	private int K;
	
	/**
	 * Dataset dimension
	 */
	private int D;
	
	/**
	 * float[][] centroids =  new float[K][D]; 
	 */
	private double[][] centroids;
	
	/**
	 * Total Within-Cluster Variation
	 */
	private double TWCV;
	
	public Centroids(Dataset dataset, Chromosom chromosom, int k){
		this.dataset 	= dataset;
		this.K 			= k;
		this.D 			= dataset.get(0).length;
		
		setCentroid(chromosom);
		setTWCV(chromosom);
	}
	
	public Centroids(Dataset dataset,int[] solutions, int k){
		this.dataset 	= dataset;
		this.K 			= k;
		this.D 			= dataset.get(0).length;
		
		setCentroid(solutions);
		setTWCV(solutions);
	}
	
	
	private void setTWCV(int[] solutions) {
		TWCV twcv = new TWCV(dataset,solutions, K, D);
		this.TWCV = twcv.getTWCV();
		
	}

	/**
	 * set centroid from chromosom
	 * @param chromosom
	 */
	public void setCentroid(Chromosom chromosom){
		this.centroids = calCentroid(chromosom);
	}
	
	/**
	 * set centroid from array of solution
	 * @param solutions
	 */
	
	public void setCentroid(int[] solutions){
		this.centroids = calCentroid(solutions);
	}
	
	/**
	 * Calculate centroids 
	 * @param chromosom a set of solution
	 * @return the centroids
	 */
	private final double[][] calCentroid(Chromosom chromosom){
		
		// init Zk and SFkd 
		int[] z 		= new int[K];
		double[][] sF 	= new double[K][D + 1]; // sum of dth feature 
		float X 		= 0;
		
		for (int i = 0; i < chromosom.size(); i++){
			//Object o	  = 
			int k = chromosom.getGen(i);
			z[k]++;
			for(int d = 0; d < D ; d++){								
				X =  dataset.get(i)[d];
				sF[k][d] = sF[k][d] + X;
			}
		}
		
		double[][] c = new double[K][D]; // centroids
		
		for(int k = 0; k < K ; k++){
			for(int d = 0; d < D ; d++){
				c[k][d] = sF[k][d]/z[k];
			}
		}
			
		return c;
	}
	
	/**
	 * calculate centroid
	 * @param solutions
	 * @return
	 */
	private final double[][] calCentroid(int[] solutions ){
		
		// init Zk and SFkd 
		int[] z 		= new int[K];
		double[][] sF 	= new double[K][D + 1]; // sum of dth feature 
		float X 		= 0;
		
		for (int i = 0; i < solutions.length; i++){
			//Object o	  = 
			int k = solutions[i];
			z[k]++;
			for(int d = 0; d < D ; d++){								
				X =  dataset.get(i)[d];
				sF[k][d] = sF[k][d] + X;
			}
		}
		
		double[][] c = new double[K][D]; // centroids
		for(int k = 0; k < K ; k++){
			for(int d = 0; d < D ; d++){
				if(sF[k][d] != 0 || z[k] != 0)
					c[k][d] = sF[k][d]/z[k];
				else 
					c[k][d] = Double.POSITIVE_INFINITY;
			}
		}
			
		return c;
	}
	
	/**
	 * 
	 * @return the centroid
	 */
	public double[][] getCentroids(){
		return this.centroids;
	}
	
	/**
	 * set TWCV
	 * @param chromosom
	 */
	public void setTWCV(Chromosom chromosom){
		TWCV twcv = new TWCV(dataset,chromosom, K, D);
		this.TWCV = twcv.getTWCV();
	}
	
	/**
	 * get TWCV
	 * @return the TWCV
	 */
	public double getTWCV(){
		return this.TWCV;
	}
	
	/**
	 * get K
	 * @return the K
	 */
	public int getK(){
		return this.K;
	}
	
	/**
	 * centroid string representation 
	 */
	public String toString(){
		String centroidString = "";
		for(int i = 0; i < K ; i++){
			centroidString = centroidString + "[";
			for(int j = 0; j < D ; j++){
				centroidString = centroidString + (float)centroids[i][j];
				if(j == D - 1) break;
				centroidString = centroidString + ",";
			}
			centroidString = centroidString + "]\n";
		}
		return centroidString;
	}
}
