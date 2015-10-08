/**
 * Copyright (c) 2006, Janusz Rybarski
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the
 * following disclaimer.
 *
 * Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions
 * and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 * AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package pl.rybarski.evolution.functions;

import java.util.ArrayList;  
//import org.apache.log4j.Logger;



import pl.rybarski.evolution.IChromosome;
import pl.rybarski.evolution.Population;

/**
 * Learning algorithm
 * 
 * @author Janusz Rybarski e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/12/28
 */


public class LearningAlgorythm {

	//static final Logger logger = Logger.getLogger(LearningAlgorythm.class);
	
	/**
	 * Reference to Reproduction model
	 */
	private AbstractReproductionFunction reproductionFunction;

	/**
	 * Reference to Cross Over function
	 */
	private AbstractCrossFunction crossFunction;

	private Population oldPopulation;

	/**
	 * New Population
	 */
	private Population newPopulation;

	/**
	 * Number of iterations
	 */
	private int maxIteration;

	/**
	 * Flag of reproducion
	 */
	private boolean makeReproduction;

	/**
	 * Flag of Cross Over
	 */
	private boolean makeCrossOver;

	/**
	 * Flag of Mutation
	 */
	private boolean makeMutation;

	/**
	 * AraryList of best chromosome in iterations
	 */
	private ArrayList<IChromosome> bestChromosome;

	/**
	 * Creates a new instance of LearningAlgorythm
	 * 
	 * @param oldPopulation
	 *            Old population
	 * @param maxIteration
	 *            Number of iteration
	 */
	public LearningAlgorythm(Population oldPopulation, int maxIteration) {
		bestChromosome = new ArrayList<IChromosome>();
		this.makeReproduction = false;
		this.makeCrossOver = false;
		this.makeMutation = false;
		this.oldPopulation = (Population) oldPopulation.clone();
		this.maxIteration = maxIteration;
	}

	/**
	 * Return best chromosome in interation
	 * 
	 * @param iteration
	 *            iteration number
	 * @return Reference to best chromosome
	 */
	public IChromosome getBestChromosome(int iteration) {
		if (bestChromosome.size() > 0) {
			return (IChromosome) bestChromosome.get(iteration);
		}
		return null;
	}

	/**
	 * Return <I>ArrayList</I> of best chromosome. Each chromosome is the best
	 * chromosome in interetion
	 * 
	 * @return ArrayList of best chromosomes
	 */
	public ArrayList getBestChromosomeList() {
		return this.bestChromosome;
	}

	/**
	 * Return reference to Cross Over Function
	 * 
	 * @return Reference to cross over function
	 */
	public AbstractCrossFunction getCrossFunction() {
		return this.crossFunction;
	}

	/**
	 * Return number of iteration
	 * 
	 * @return Return number of iteration
	 */
	public int getMaxIteration() {
		return maxIteration;
	}

	/**
	 * Return reference to new population
	 * 
	 * @return Reference to new population
	 */
	public Population getNewPoplation() {
		return this.newPopulation;
	}

	/**
	 * Return reference to populatoin before learning
	 * 
	 * @return Reference to population before learning
	 */
	public Population getOldPopulation() {
		return this.oldPopulation;
	}

	/**
	 * Return reference to reproduction Function Model
	 * 
	 * @return Reference to reproducion function model
	 */
	public AbstractReproductionFunction getReproductionFunction() {
		return reproductionFunction;
	}

	/**
	 * Check if cross over is used in learning algorithm
	 * 
	 * @return return information about cross over
	 */
	public boolean isMakeCrossOver() {
		return this.makeCrossOver;
	}

	/**
	 * Check if mutation is used in learning algorithm
	 * 
	 * @return return information about mutation
	 */
	public boolean isMakeMutation() {
		return this.makeMutation;
	}

	/**
	 * Check if reproduction is used in learning algorithm
	 * 
	 * @return return information about reproduction
	 */
	public boolean isMakeReproduction() {
		return this.makeReproduction;
	}

	/**
	 * Start learning algorithm
	 */
	public void learn() {
		Population tempPopulation = (Population) oldPopulation.clone();
		for (int i = 0; i < maxIteration; i++) {
			if (makeReproduction) {
				reproductionFunction.setOldPopulation(tempPopulation);
				reproductionFunction.generateNewPopulation();
				tempPopulation = (Population) reproductionFunction
						.getNewPopulation().clone();
			}

			if (makeMutation) {
				IChromosome tempChromosome;
				int chromosomeSize = tempPopulation.getChromosomeSize();
				for (int j = 0; j < tempPopulation.size(); j++) {
					tempChromosome = tempPopulation.getChromosome(j);
					for (int k = 0; k < chromosomeSize; k++) {
						if (tempChromosome.geneMutation(k)) {
							//logger.info("Chromosome " + j + ", gene " + k
							//		+ " has been mutated");
						}
					}
				}
			}

			if (makeCrossOver) {

				crossFunction.setOldPopulation(tempPopulation);
				crossFunction.generateNewPopulation();
				tempPopulation = (Population) crossFunction.getNewPopulation()
						.clone();
			}

			bestChromosome.add((IChromosome) (tempPopulation
					.getBestChromosome()).clone());
			newPopulation = (Population) tempPopulation.clone();
			
		}
	}

	/**
	 * Set reference to Cross Over funciton
	 * 
	 * @param crossFunctionModel
	 *            reference to Cross Over Function
	 */
	public void setCrossFunction(AbstractCrossFunction crossFunctionModel) {
		this.crossFunction = crossFunctionModel;
		this.makeCrossOver = true;
	}

	/**
	 * Set using cross over in learning
	 * 
	 * @param makeCrossOver
	 *            Set using cross over in learning
	 */
	public void setMakeCrossOver(boolean makeCrossOver) {
		this.makeCrossOver = makeCrossOver;
	}

	/**
	 * Set using mutation in learning
	 * 
	 * @param makeMutation
	 *            Set using mutation in learning
	 */
	public void setMakeMutation(boolean makeMutation) {
		this.makeMutation = makeMutation;
	}

	/**
	 * Set using reproducion in learning
	 * 
	 * @param makeReproduction
	 *            Set using reproducion in learning
	 */
	public void setMakeReproduction(boolean makeReproduction) {
		this.makeReproduction = makeReproduction;
	}

	/**
	 * Set iteration number
	 * 
	 * @param maxIteration
	 *            Iteration number
	 */
	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	/**
	 * Set old population
	 * 
	 * @param oldPopulation
	 *            Reference to population
	 */
	public void setOldPopulation(Population oldPopulation) {
		this.oldPopulation = (Population) oldPopulation.clone();
	}

	/**
	 * Set reproducion function model
	 * 
	 * @param reproductionFunciton
	 *            Reference to reproducion function model
	 */
	public void setReproductionFunction(
			AbstractReproductionFunction reproductionFunciton) {
		this.reproductionFunction = reproductionFunciton;
		this.makeReproduction = true;
	}
}
