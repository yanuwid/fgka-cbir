package com.tanomatics.java.ml.clustering.fgka.ga;

import java.util.ArrayList;

import pl.rybarski.evolution.IChromosome;
import pl.rybarski.evolution.functions.IFitnessFunction;


/**
 * A Class for calculating specified chromosom fitness value 
 * @author yanuwid
 * @version 1.0
 */
public class FitnessFunction implements IFitnessFunction{

	private Population population;
	private double maxTWCV;
	private ArrayList<Double> legalFitnessValue;
	private double minFitness;
	
	/**
	 * 
	 * @param population
	 */
	public FitnessFunction(Population population){
		this.population = (Population) population.clone();
		legalFitnessValue = new ArrayList<Double>();
		maxTWCV = population.getMaxTWCVChromosom().getTWCV();
		setLegalFitnessValue();
		setMinFitnessValue();
	}
		

	/**
	 * set minimum fitness value
	 *
	 */
	private void setMinFitnessValue() {
		double min = Double.POSITIVE_INFINITY;
		if(legalFitnessValue.size() == 0){
			min = 1;
		} else {
			for(int i = 0; i < legalFitnessValue.size(); i ++){
				if(legalFitnessValue.get(i) < min)
					min = legalFitnessValue.get(i);
			}
		}
		
		this.minFitness = min;
	}


	/**
	 * set the fitness value of the legal solution
	 *
	 */
	private void setLegalFitnessValue(){
		
		double T1 = 0;
		double T2 = 0;
		
		for(int i = 0; i < population.size(); i++){
			if( population.getChromosom(i).isLegal()){
				T1 = 1.5 * maxTWCV;
				T2 = population.getChromosom(i).getTWCV();
				legalFitnessValue.add( T1 - T2 );
			}
		}
	}

	/**
	 * get value
	 */
	public double getValue(IChromosome chromosome) {	
		Chromosom chromosom = (Chromosom) chromosome.clone();
		
		double legalVal = (1.5 * maxTWCV) - (chromosom.getTWCV());
		double ilegalVal = chromosom.getLegalityRatio() * minFitness;
		
		if(chromosom.isLegal()){
			chromosom = null;
			return  (legalVal);
		}
		else {
			chromosom = null;
			return (ilegalVal);
		}	
	}

}
