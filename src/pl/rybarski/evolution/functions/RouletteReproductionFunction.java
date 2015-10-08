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
import pl.rybarski.evolution.IChromosome;
import pl.rybarski.evolution.Population;

/**
 * Rulette Reproduction function
 * 
 * @author Janusz Rybarski e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */
public class RouletteReproductionFunction extends AbstractReproductionFunction {

	/**
	 * Array of weel value for each chromosome in population
	 */
	private Double[] wheelValue;

	/**
	 * Creates a new instance of RouletteReproductionFunction for specified
	 * population
	 */
	public RouletteReproductionFunction() {

	}

	@Override
	public void setOldPopulation(Population oldPopulation) {
		super.setOldPopulation(oldPopulation);
		wheelValue = new Double[oldPopulation.size()];
	}

	/**
	 * Starts Reproduction according to roulette function
	 */
	public void generateNewPopulation() {
		int chromosomeIndex = 0;
		double sum = 0, point = 0, wheelTemp = 0, tempValue = 0;
		java.util.Random random = new java.util.Random();

		newPopulaton = new Population();
		newPopulaton.setFitnessFunction(oldPopulation.getFitnessFunction());

		int oldPopulationSize = oldPopulation.size();
		Double[] fitnessValue = new Double[oldPopulationSize];
		double value = 0;
		for (int i = 0; i < oldPopulationSize; i++) {
			value = oldPopulation.getFitnessValue(i);
			fitnessValue[i] = value;
			sum += value;
		}

		for (int i = 0; i < oldPopulationSize; i++) {
			
			tempValue = fitnessValue[i] / sum;
			//System.out.println("temp value "+tempValue);
			wheelTemp += tempValue;
			wheelValue[i] = wheelTemp;
			//System.out.println(wheelValue[i]);
		}
		//System.out.println();

		for (int i = 0; i < oldPopulationSize; i++) {
			point = random.nextDouble();
			//System.out.println(point);
			for (int j = 0; j < oldPopulation.size(); j++) {
				if (wheelValue[j] > point) {
					chromosomeIndex = j;
					break;
				}
				chromosomeIndex = 0;
			}
			IChromosome choosenChromosome = oldPopulation
					.getChromosome(chromosomeIndex);
			newPopulaton.addChromosome((IChromosome) choosenChromosome.clone());
		}
	}
}
