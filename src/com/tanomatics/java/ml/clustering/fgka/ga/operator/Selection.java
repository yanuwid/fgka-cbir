package com.tanomatics.java.ml.clustering.fgka.ga.operator;


import java.util.Random;

import com.tanomatics.java.ml.clustering.fgka.ga.Chromosom;
import com.tanomatics.java.ml.clustering.fgka.ga.FitnessFunction;
import com.tanomatics.java.ml.clustering.fgka.ga.Population;

/**
 * FGKA Selection Operator 
 * (See Yi Lu, Shiyong Lu, Farshad Fotouhi, Youping Deng, Susan J. Brown, 
 * <i>Fast Genetic K-means Algorithm and its Application in Gene Expression 
 * Data Analysis</i>)
 * @author yanuwid
 * @version 1.0 
 */
public class Selection {
	
	private Population newPopulation;
	private FitnessFunction fitnessFunction;
	
	public Selection(){
	}
	
	/**
	 * @param population
	 */
	public void doSelection(Population population){
		
		Population oldPopulation = (Population) population.clone();
		newPopulation = (Population) oldPopulation.clone();
		
		double[] f = new double[oldPopulation.size()];
		double sF = 0;
		for(int i = 0; i < oldPopulation .size(); i++){
			f[i] = oldPopulation.getFitnessValue(i);
			sF = sF + f[i];
		}
		
		double p = 0; //sum of probability value
		double[] distibution = new double[oldPopulation.size()];
		for(int i = 0; i < oldPopulation.size(); i++){
			p = p + (f[i]/sF);
			distibution[i] = p;
		}
		
		Random random = new Random();
		newPopulation.clear();
		double selectPoint = 0;
		int index = 0;
		Chromosom chromosom = null;
		for(int i = 0; i < oldPopulation.size(); i++){
			for(int j = 0; j < oldPopulation.size(); j++){
				selectPoint = random.nextDouble();
				if(distibution[j] > selectPoint){
					index = j;
					break;
				}
				index = 0;
			}
			chromosom = (Chromosom) oldPopulation.getChromosom(index).clone();
			newPopulation.addChromosom(chromosom);
		}
	}
	
	public Population getNewPopulation(){
		fitnessFunction = new FitnessFunction(newPopulation);
		newPopulation.setFitnessFunction(fitnessFunction);
		return this.newPopulation;
	}
	
}
