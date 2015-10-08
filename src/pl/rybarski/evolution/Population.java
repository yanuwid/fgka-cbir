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

package pl.rybarski.evolution;

import java.util.ArrayList;

import pl.rybarski.evolution.functions.IFitnessFunction;

/**
 * Population which is a set of chromosomes
 * 
 * @author Janusz Rybarski e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */

public class Population implements Cloneable {

	private IFitnessFunction fitnessFunction;

	/**
	 * ArrayList of chromosome
	 */
	private ArrayList<IChromosome> population;

	/**
	 * Create new instance of population. Population size is set to <I>0</I>
	 */
	public Population() {
		population = new ArrayList<IChromosome>();
	}

	/**
	 * Add chromosome to the population at the end. Population size increments
	 * 
	 * @param tempChromosome
	 *            Reference to chromosome
	 */
	public void addChromosome(IChromosome tempChromosome) {
		population.add(tempChromosome);
	}

	/**
	 * Create and return copy of population
	 * 
	 * @return copy of the population
	 */
	public Object clone() {
		Population populationCopy = new Population();
		populationCopy.fitnessFunction = fitnessFunction;
		for (int i = 0; i < size(); i++) {
			populationCopy.addChromosome((IChromosome) population.get(i)
					.clone());
		}
		return populationCopy;
	}

	/**
	 * Indicates whether some other object is "equal to" this network.
	 * 
	 * @param obj
	 *            the reference object to compare with
	 * @return <I>true</I> if this chromosome is the same as the obj argument;<I>
	 *         false</I> otherwise
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Population) {
			Population tempPopulation = (Population) obj;
			if (size() != tempPopulation.size())
				return false;

			for (int i = 0; i < size(); i++) {
				if (!population.get(i).equals(tempPopulation.getChromosome(i)))
					return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return reference to chromosome with best fitness function value
	 * 
	 * @return reference to chromosome
	 */
	public IChromosome getBestChromosome() {
		IChromosome bestChromosome = null;
		double bestFittnesValue = 0;

		for (int i = 0; i < size(); i++) {
			if (getFitnessValue(i) > bestFittnesValue) {
				bestChromosome = population.get(i);
				bestFittnesValue = getFitnessValue(bestChromosome);
			}
		}
		return bestChromosome;
	}

	/**
	 * Return reference to chromosome specified by index
	 * 
	 * @param index
	 *            chromosome index
	 * @return reference to chromosome
	 */
	public IChromosome getChromosome(int index) {
		return population.get(index);
	}

	/**
	 * Return size of chromosome
	 * 
	 * @return size of chromosome
	 */
	public int getChromosomeSize() {
		if (size() > 0)
			return population.get(0).size();
		else
			return 0;
	}

	public IFitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	public double getFitnessValue(IChromosome chromosome) {
		return fitnessFunction.getValue(chromosome);
	}

	public double getFitnessValue(int chromosomeNumber) {
		return fitnessFunction.getValue(population.get(chromosomeNumber));
	}

	/**
	 * Return population size
	 * 
	 * @return population size
	 */
	public int size() {
		return population.size();
	}

	/**
	 * Clear population. Population size is set to <I>0</I>
	 */
	public void removeAllChromosome() {
		population.clear();
	}

	/**
	 * Remove chromosome specified by index. Population size is decrease.
	 * 
	 * @param index
	 *            index of the chromosome
	 */
	public void removeChromosome(int index) {
		population.remove(index);
	}

	/**
	 * Replece chromosome specified by index by chromosome
	 * 
	 * @param index
	 *            chromosome which must by replace
	 * @param tempChromosome
	 *            reference to new chromosome
	 */
	public void replaceChromosome(int index, IChromosome tempChromosome) {
		population.set(index, tempChromosome);
	}

	public void setFitnessFunction(IFitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	/**
	 * Return <I>String</I> representation of population
	 * 
	 * @return Strin representation of population
	 */
	public String toString() {
		String populationText = "";
		if (fitnessFunction != null) {
			for (int i = 0; i < size(); i++) {
				populationText += population.get(i).toString() + " - "
						+ getFitnessValue(i) + "\n";
			}
		} else {
			for (int i = 0; i < size(); i++) {
				populationText += population.get(i).toString() + "\n";
			}

		}
		return populationText;
	}
}
