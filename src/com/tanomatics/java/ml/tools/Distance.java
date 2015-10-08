package com.tanomatics.java.ml.tools;


/**
 * A class for calculating distance between two dataset
 * @author yanuwid
 * @version 1.0
 */
public class Distance {
	
	/**
	 * @param dataset1 a dataset1
	 * @param dataset2 a dataset2
	 * @return the distance
	 */
	public static double getDistance(double[] dataset1, double[] dataset2){
		double total = 0;
		for(int i = 0; i < dataset1.length; i++){
			total = total + Math.pow(dataset1[i]-dataset2[i],2);
		}
		return Math.sqrt(total);
	}
	
	/**
	 * @param dataset1 
	 * @param dataset2
	 * @return the distance
	 */
	public static double getDistance(float[] dataset1, float[] dataset2){
		double[] dataset_a = new double[dataset1.length];
		double[] dataset_b = dataset_a.clone();
		
		for(int i = 0; i < dataset_a.length; i++){
			dataset_a[i] = dataset1[i];
			dataset_b[i] = dataset2[i];
		}
		
		return getDistance(dataset_a, dataset_b);
	}
	
	/**
	 * @param dataset1 
	 * @param dataset2
	 * @return the distance
	 */
	public static double getDistance(Object[] dataset1, Object[] dataset2){
		double[] dataset_a = new double[dataset1.length];
		double[] dataset_b = dataset_a.clone();
		for(int i = 0; i < dataset_a.length; i++){
			dataset_a[i] = (Double)dataset1[i];
			dataset_b[i] = (Double)dataset2[i];
		}
		return getDistance(dataset_a, dataset_b);
	}

}
