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

/**
 * Chromosome with bit representation
 * 
 * @author Janusz Rybarski e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */

public class Chromosome implements IChromosome, Cloneable {

	/**
	 * Array of gene
	 */
	private Object[] geneList;

	/**
	 * chromosome size
	 */
	private int chromosomeSize;

	/**
	 * cross over propability
	 */
	private double crossOverProbability = 1.0;

	/**
	 * Mutation propability
	 */
	private double mutationProbability = 0.0;

	/**
	 * Creates a new instance of Chromosome
	 * 
	 * @param chromosomeSize
	 *            size of chromosome
	 * @param fitnessFunction
	 *            reference to fitness function
	 * @param toStringFunction
	 *            Reference to toStringFunction
	 * @param geneTypes
	 *            Array of gene types
	 */
	public Chromosome(int chromosomeSize,IGeneToString toStringFunction, Object[] geneTypes) {
		this.chromosomeSize = chromosomeSize;
		this.chromosomeSize = chromosomeSize;
		geneList = new Object[chromosomeSize];
		for (int i = 0; i < chromosomeSize; i++)
			geneList[i] = new Gene(geneTypes, toStringFunction);
	}

	/**
	 * Creates a new instance of Chromosome
	 * 
	 * @param chromosomeSize
	 *            chromosome size
	 * @param crossOverProbability
	 *            cross over propability
	 * @param fitnessFunction
	 *            reference to fitness function
	 * @param toStringFunction
	 *            Reference to toStringFunction
	 * @param geneTypes
	 *            Array of gene types
	 */
	public Chromosome(int chromosomeSize, double crossOverProbability, IGeneToString toStringFunction,
			Object[] geneTypes) {
		this(chromosomeSize, toStringFunction, geneTypes);
		this.crossOverProbability = crossOverProbability;
	}

	/**
	 * Set mutation probability
	 * 
	 * @param mutationProbability
	 *            Mutation propability
	 */
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	/**
	 * Set gene with specified value
	 * 
	 * @param geneNumber
	 *            Gene number
	 * @param value
	 *            Gene value
	 */
	public void setGene(int geneNumber, Object value) {
		Gene tempGene = (Gene) geneList[geneNumber];
		tempGene.setGene(value);
	}

	/**
	 * Return mutation propability
	 * 
	 * @return Mutation propability
	 */
	public double getMutationProbability() {
		return this.mutationProbability;
	}

	/**
	 * Return gene for with specified gene NUmber
	 * 
	 * @return Gene
	 * @param geneNumber
	 *            Gene number
	 */
	public Object getGene(int geneNumber) {
		return geneList[geneNumber];
	}

	/**
	 * Return cross over propability
	 * 
	 * @return cross over propability
	 */
	public double getCrossOverProbability() {
		return this.crossOverProbability;
	}

	/**
	 * Set propability of cross over
	 * 
	 * @param crossOverProbality
	 *            cross over propability
	 */
	public void setCrossOverProbability(double crossOverProbality) {
		this.crossOverProbability = crossOverProbality;
	}


	/**
	 * Return size of the chromosome
	 * 
	 * @return Size of the chromosome
	 */
	public int size() {
		return this.chromosomeSize;
	}

	/**
	 * Mutant gene specified by genNumber. Return information about mutation
	 * 
	 * @param geneNumber
	 *            gene Number
	 * @return information about mutation
	 */
	public boolean geneMutation(int geneNumber) {
		return ((Gene) geneList[geneNumber]).mutation();
	}

	/**
	 * Return string representation of the chromosome
	 * 
	 * @return string representation of the chromosome
	 */
	public String toString() {
		String chromosomeString = "";
		for (int i = 0; i < chromosomeSize; i++) {
			chromosomeString += geneList[i].toString();
		}
		return chromosomeString;
	}

	/**
	 * Indicates whether some other object is "equal to" this network.
	 * 
	 * @param obj
	 *            the reference object to compare with
	 * @return <I>true</I> if this chromosome is the same as the obj argument;<I>
	 *         false</I> otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof IChromosome) {
			IChromosome chromosome = (IChromosome) obj;
			if (this.chromosomeSize != chromosome.size())
				return false;

			for (int i = 0; i < chromosomeSize; i++) {
				if (geneList[i] != chromosome.getGene(i))
					;
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return copy of the chromosome
	 * 
	 * @return Clone of chromosome
	 */
	public Object clone() {
		try {
			Chromosome chromosomeCopy = (Chromosome) super.clone();
			chromosomeCopy.chromosomeSize = chromosomeSize;
			chromosomeCopy.crossOverProbability = crossOverProbability;
			chromosomeCopy.mutationProbability = mutationProbability;
			chromosomeCopy.geneList = (Object[]) geneList.clone();
			for (int i = 0; i < chromosomeSize; i++) {
				geneList[i] = ((Gene) geneList[i]).clone();
			}
			return chromosomeCopy;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
