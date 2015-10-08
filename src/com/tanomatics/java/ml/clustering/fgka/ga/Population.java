package com.tanomatics.java.ml.clustering.fgka.ga;


import java.util.ArrayList;

import com.tanomatics.java.ml.tools.DoubleFormatter;

/**
 * Population of solutions
 * @author yanuwid
 * @version 1.0
 */
public class Population {
	
	private FitnessFunction fitnessFunction;
	
	private ArrayList<Chromosom> population;
	
	public Population(){
		population = new ArrayList<Chromosom>();
	}
	
	/**
	 * 
	 * @param fitnessFunction
	 */
	public Population(FitnessFunction fitnessFunction){
		population = new ArrayList<Chromosom>();
		this.fitnessFunction = fitnessFunction;
	}
	
	/**
	 * get chromosom
	 * @param index
	 * @return the chromosom
	 */
	public Chromosom getChromosom(int index){
		return population.get(index);
	}
	
	/**
	 * add chromosom
	 * @param chromosom
	 */
	public void addChromosom(Chromosom chromosom){
		population.add(chromosom);
	}
	
	/**
	 * set fitness function
	 * @param fitnessFunction
	 */
	public void setFitnessFunction(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}
	
	/**
	 * get the size of population
	 * @return
	 */
	public int size() {
		return population.size();
	}
	
	/**
	 * get fitness value from specified chromosom
	 */
	public double getFitnessValue(int chromosomeNumber) {
		return fitnessFunction.getValue(population.get(chromosomeNumber));
	}
	
	/**
	 * get chromosom with maximum TWCV
	 * @return the chromosom
	 */
	public Chromosom getMaxTWCVChromosom(){
		double max = 0;
		int index = 0;
		for(int i = 0; i < size(); i++){
			if(!(population.get(i).getTWCV() == Double.POSITIVE_INFINITY)){
				if (population.get(i).getTWCV() > max ){
					max = population.get(i).getTWCV();
					index = i;
				}
			} else continue; 
		}
		return population.get(index);
	}
	

	public Object clone() {
		Population populationCopy = new Population();
		populationCopy.setFitnessFunction(fitnessFunction);
		for (int i = 0; i < size(); i++) {
			populationCopy.addChromosome((Chromosom) population.get(i)
					.clone());
		}
		return populationCopy;
	}
	
	private void addChromosome(Chromosom chromosome) {
		this.population.add(chromosome);
		
	}
	
	public void clear(){
		this.population.clear();
	}
	

	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (fitnessFunction != null) {
			for (int i = 0; i < size(); i++) {
				sb.append(population.get(i));
				sb.append(" fit=" +DoubleFormatter.customDecimalFormat(getFitnessValue(i)));
				sb.append("\n");
			}
		} else {
			for (int i = 0; i < size(); i++){ 
				sb.append(population.get(i));
				sb.append("\n");
			}
			
		}
		return sb.toString() ;
	}

}
