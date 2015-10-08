package com.tanomatics.java.ml.clustering.fgka.ga.operator;


import java.util.Random;

import com.tanomatics.java.ml.clustering.Centroids;
import com.tanomatics.java.ml.clustering.fgka.ga.Chromosom;
import com.tanomatics.java.ml.clustering.fgka.ga.FitnessFunction;
import com.tanomatics.java.ml.clustering.fgka.ga.Population;
import com.tanomatics.java.ml.core.Dataset;
import com.tanomatics.java.ml.tools.Distance;

/**
 * FGKA Mutation Operator
 * (See Yi Lu, Shiyong Lu, Farshad Fotouhi, Youping Deng, Susan J. Brown, 
 * <i>Fast Genetic K-means Algorithm and its Application in Gene Expression 
 * Data Analysis</i>)
 * @author yanuwid
 * @version 1.0
 */
public class Mutation {

	private Population newPopulation;
	private Dataset dataset;
	private FitnessFunction fitnessFunction;
	
	/**
	 * @param dataset
	 */
	public Mutation(Dataset dataset){	
		this.dataset = dataset;
	}
	
	/**
	 * @param population
	 * @param mutationProbability
	 */
	public void doMutation(Population population, double mutationProbability){
		Population oldPopulation 	= (Population) population.clone();
		newPopulation			 	= (Population) oldPopulation.clone();
		Random random 			 	= new Random();
		Chromosom chromosom 	 	= null;
		Centroids centroid 		 	= null;
		double SD, dMax			 	= 0;
		double[] d				 	= null;
		double[] dataset1,dataset2 	= null;
		double[] distribution		= null;
		double p 					= 0;
		Object value				= 0;
		float[] tmp 				= null;
		
		newPopulation.clear();
		
		for(int i = 0; i < oldPopulation.size(); i++){
			chromosom 	= (Chromosom) oldPopulation.getChromosom(i).clone();
			centroid  	= chromosom.getCentroid();
			SD 			= 0;
			d 			= new double[centroid.getK()];
			
			for(int j = 0; j < chromosom.size(); j++){
				boolean isTrue = random.nextDouble() < mutationProbability; 
				if(isTrue){
					dMax = 0;
					for(int k = 0; k < centroid.getK(); k++){
						tmp = dataset.get(j);
						dataset1 = new double[tmp.length];
						for(int l = 0; l < tmp.length; l++)
							dataset1[l] = tmp[l];
						
						dataset2 = centroid.getCentroids()[k];
						d[k] = Distance.getDistance(dataset1, dataset2);
						//System.out.println("-----------"+d[k]);
						dMax = Math.max(d[k], dMax);
					}
					
					//System.out.println(dMax);
					
					for(int k = 0; k < centroid.getK(); k++){
						SD = SD + ((1.5 * dMax) - (d[k] + 0.5));
					}
					
					p = 0;
					
					distribution = new double[centroid.getK()];
					
					for (int k = 0; k < centroid.getK(); k++){
						p = p + ((1.5 * dMax) - (d[k] + 0.5))/SD;
						distribution[k] = p;
					}
					
					value = 0;
					for (int k = 0; k < centroid.getK(); k++){	
						if(distribution[k] > random.nextDouble()){
							value = k;
							break;
						}
					}
									
					chromosom.setGene(j, value);
				} // end of if
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
