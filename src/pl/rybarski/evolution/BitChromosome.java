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

import java.util.Random;

/**
 * Chromosome with bit representation
 * 
 * @author Janusz Rybarski e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */

public class BitChromosome implements IChromosome {

	/**
	 * Reference to random object
	 */
	private static Random random = new Random();

	/**
	 * Gene to string function
	 */
	private IGeneToString geneToString;

	/**
	 * List of genes
	 */
	private Object[] geneList;

	/**
	 * Cross over propability
	 */
	private double crossOverProbability = 1.0;

	/**
	 * Mutation propability
	 */
	private double mutationProbability = 0.0;

	/**
	 * Creates a new instance of BitChromosome
	 * 
	 * @param chromosomeSize
	 *            Chromosome size
	 * @param crossOverProbability
	 *            cross over propability
	 * @param mutationProbability
	 *            mutation propability
	 * @param fitnessFunction
	 *            reference to fitness function
	 */

	private BitChromosome(double crossOverProbability,
			double mutationProbability) {
		this.crossOverProbability = crossOverProbability;
		this.mutationProbability = mutationProbability;
	}

	public BitChromosome(int chromosomeSize, double crossOverProbability,
			double mutationProbability) {
		this(chromosomeSize);
		this.crossOverProbability = crossOverProbability;
		this.mutationProbability = mutationProbability;
	}

	/**
	 * Creates a new instance of BitChromosome with<BR>
	 * <I>Cross over probability</I> - set to <I>1.0</I> <I>Mutatation
	 * probability</I> - set to <I>0</I>
	 * 
	 * @param chromosomeSize
	 *            size of chromosome
	 * @param fitnessFunction
	 *            reference to fitness function
	 */
	public BitChromosome(int chromosomeSize) {
		geneList = new Object[chromosomeSize];
		double bitValue;
		for (int i = 0; i < chromosomeSize; i++) {
			bitValue = random.nextDouble();
			if (bitValue < 0.5) {
				geneList[i] = true;
			} else {
				geneList[i] = false;
			}
		}
	}

	/**
	 * Creates a new instance of BitChromosome from Chromosome String where:
	 * <BR>
	 * <I>1</I> represent gene with <I>true</I> value <I>0</I> represent gene
	 * with <I>falce</I> value
	 * 
	 * @param chromosomeString
	 *            String representation of chromosome
	 * @param fitnessFunction
	 *            reference to fitness function
	 */
	public BitChromosome(String chromosomeString) {
		geneList = new Object[chromosomeString.length()];
		char ch;
		for (int i = 0; i < geneList.length; i++) {
			ch = chromosomeString.charAt(i);
			if (ch == '1') {
				geneList[i] = true;
			} else {
				geneList[i] = false;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#clone()
	 */
	public Object clone() {
		BitChromosome chromosomeCopy = new BitChromosome(crossOverProbability,
				mutationProbability);
		chromosomeCopy.geneList = (Object[]) geneList.clone();
		return chromosomeCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof BitChromosome) {
			IChromosome chromosome = (IChromosome) obj;
			if (geneList.length != chromosome.size())
				return false;

			for (int i = 0; i < geneList.length; i++) {
				if (geneList[i] != chromosome.getGene(i))
					;
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#geneMutation(int)
	 */
	public boolean geneMutation(int geneNumber) {
		double probability = random.nextDouble();
		if (probability < mutationProbability) {
			if ((java.lang.Boolean) geneList[geneNumber])
				geneList[geneNumber] = false;
			else
				geneList[geneNumber] = true;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#getChromosomeSize()
	 */
	public int size() {
		return geneList.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#getCrossOverProbability()
	 */
	public double getCrossOverProbability() {
		return this.crossOverProbability;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#getGene(int)
	 */
	public Object getGene(int geneNumber) {
		return geneList[geneNumber];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#getMutationProbability()
	 */
	public double getMutationProbability() {
		return this.mutationProbability;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#setCrossOverProbability(double)
	 */
	public void setCrossOverProbability(double crossOverProbality) {
		this.crossOverProbability = crossOverProbality;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#setGene(int,
	 *      java.lang.Object)
	 */
	public void setGene(int geneNumber, Object value) {
		try {
			geneList[geneNumber] = (java.lang.Boolean) value;
		} catch (Exception e) {
			System.out
					.println("Value must be type of boolean. Cannot set new gene value");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#setGeneToString(pl.rybarski.evolution.IGeneToString)
	 */
	public void setGeneToString(IGeneToString genToString) {
		this.geneToString = genToString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#setMutationProbability(double)
	 */
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.rybarski.evolution.AbstractChromosome#toString()
	 */
	public String toString() {
		String chromosomeString = "";

		for (int i = 0; i < geneList.length; i++) {
			if (geneToString != null) {
				chromosomeString += geneToString.toString(geneList[i]);
			} else {
				if ((java.lang.Boolean) geneList[i])
					chromosomeString += "1";
				else
					chromosomeString += "0";
			}
		}
		return "[" + chromosomeString + "]";
	}
}
