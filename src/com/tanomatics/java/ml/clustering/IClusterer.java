package com.tanomatics.java.ml.clustering;

/**
 * clusterer interface
 * @author yanu
 * 
 */
public interface IClusterer {

	/**
	 * do clustering
	 */
	public abstract void doClustering();

	/**
	 * @return array of clustering result 
	 */
	public abstract int[] getResults();

	/**
	 * @param index
	 * @return result of clustering at specified index
	 */
	public abstract int getResults(int index);

	/**
	 * @param index
	 * @return array of centroid at specified index
	 */
	public abstract float[] getCentroid(int index);

}