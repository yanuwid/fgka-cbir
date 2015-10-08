package com.tanomatics.java.ml.clustering.fgka.ga.operator;

import com.tanomatics.java.ml.clustering.Centroids;
import com.tanomatics.java.ml.clustering.fgka.ga.Chromosom;
import com.tanomatics.java.ml.clustering.fgka.ga.FitnessFunction;
import com.tanomatics.java.ml.clustering.fgka.ga.Population;
import com.tanomatics.java.ml.core.Dataset;
import com.tanomatics.java.ml.tools.Distance;


/**
 * FGKA KMeans Operator (KMO)
 * (See Yi Lu, Shiyong Lu, Farshad Fotouhi, Youping Deng, Susan J. Brown, 
 * <i>Fast Genetic K-means Algorithm and its Application in Gene Expression 
 * Data Analysis</i>)
 * @author yanuwid
 * @version 1.0
 */
public class KMeans {

	private Dataset dataset;
	private Population newPopulation;
	private FitnessFunction fitnessFunction;
	
	/**
	 * @param dataset
	 */
	public KMeans(Dataset dataset){
		this.dataset = dataset;
	}
	
	/**
	 * @param population
	 */
	public void doClustering(Population population){
		Population oldPopulation 	= (Population) population.clone();
		newPopulation			 	= (Population) oldPopulation.clone();
		Chromosom chromosom 	 	= null;
		Centroids centroid 		 	= null;
		double[] dataset1,dataset2 	= null;
		double dmin 			 	= 0;
		double[] d				 	= null;
		Object kmin				 	= null;
		float[] tmp			 	= null;
		
		newPopulation.clear();
		
		for(int i = 0; i < oldPopulation.size(); i++) {
			
			chromosom 	= (Chromosom) oldPopulation.getChromosom(i).clone();
			centroid  	= chromosom.getCentroid();
			d 			= new double[centroid.getK()];
			kmin		= 0;
			
			for(int j = 0; j < chromosom.size(); j++) {
				dmin = Double.POSITIVE_INFINITY;
				for(int k = 0; k < centroid.getK(); k++){
					tmp = dataset.get(j);
					dataset1 = new double[tmp.length];
					for(int l = 0; l < tmp.length; l++)
						dataset1[l] = tmp[l];
					dataset2 = centroid.getCentroids()[k];
					d[k] = Distance.getDistance(dataset1, dataset2);
					if(d[k] < dmin){
						dmin = d[k];
						kmin = k;
					}
				}
				chromosom.setGene(j, kmin);
			}
			chromosom.setCentroid(dataset,chromosom,centroid.getK());
			newPopulation.addChromosom(chromosom);
			
		}
	}
	
	public Population getNewPopulation(){
		fitnessFunction = new FitnessFunction(newPopulation);
		newPopulation.setFitnessFunction(fitnessFunction);
		return this.newPopulation;
	}
}
