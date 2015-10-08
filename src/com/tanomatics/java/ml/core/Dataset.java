package com.tanomatics.java.ml.core;

import java.util.ArrayList;
/**
 * A Class representing dataset
 * @author yanuwid
 *
 */
public class Dataset {
	
	private ArrayList<float[]> datasetArrayList ;
	
	public Dataset(){
		datasetArrayList = new ArrayList<float[]>();
	}
	
	/**
	 * adding dataset
	 * @param dataset
	 */
	public void add(float[] dataset) {
		this.datasetArrayList.add(dataset);
	}
	
	/**
	 * adding dataset
	 * @param dataset
	 */
	public void add(double[] dataset){
		float[] tempDataset = new float[dataset.length];
		for(int i = 0; i < tempDataset.length; i++){
			tempDataset[i] = (float) dataset[i];
		}
		this.datasetArrayList.add(tempDataset);
	}
	
	/**
	 * get dataset
	 * @param index
	 * @return
	 */
	public float[] get(int index){
		return datasetArrayList.get(index);
	}
	
	/**
	 * maximum Value
	 * @return the max value
	 */
	public float maxValue(){
		float max = 0;
		for(int i = 0; i < datasetArrayList.size(); i++){
			for(int j = 0; j < datasetArrayList.get(i).length; j++){
				if(datasetArrayList.get(i)[j] > max){
					max = datasetArrayList.get(i)[j];
				}
			}
		}
		
		return max;
	}
	
	/**
	 * minimum value
	 * @return the min value
	 */
	public float minValue(){
		float min = Float.POSITIVE_INFINITY;
		for(int i = 0; i < datasetArrayList.size(); i++){
			for(int j = 0; j < datasetArrayList.get(i).length; j++){
				if(datasetArrayList.get(i)[j] > min){
					min = datasetArrayList.get(i)[j];
				}
			}
		}
		return min;
	}
	
	/**
	 * is empty checking
	 * @return isEmpty
	 */
	public boolean isEmpty(){
		return datasetArrayList.isEmpty();
	}
	
	/**
	 * clearing dataset from unused data
	 */
	public void clear(){
		if(!datasetArrayList.isEmpty())
		datasetArrayList.clear();
	}
	
	/**
	 * 
	 * @return the size of dataset
	 */
	public int size(){
		return datasetArrayList.size();
	}
	
	
	public Object clone(){
		Dataset dataset = new Dataset();
		dataset.datasetArrayList = this.datasetArrayList;
		return dataset;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < datasetArrayList.size(); i++) {
			sb.append("[");
			for(int j = 0; j < datasetArrayList.get(i).length; j++){
				sb.append((int)datasetArrayList.get(i)[j]);
				if(j == (datasetArrayList.get(i).length- 1)) break;
				sb.append(",");
			}
			sb.append("] \n");
		}
		return sb.toString();
	}
	
}
