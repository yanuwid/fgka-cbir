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

import java.util.Random;
import pl.rybarski.evolution.IChromosome;
import pl.rybarski.evolution.Population;

/**
 * Default Cross Over function
 * 
 * @author Janusz Rybarski e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */
public class DefaultCrossFunction extends AbstractCrossFunction {

	public DefaultCrossFunction() {

	}

	/**
	 * Creates a new instance of DefaultCrossFunctionModel for specified
	 * population. Cross Over Propability is set to <I>1.0</I>
	 * 
	 * @param oldPopulation
	 *            reference to population
	 * @see Population
	 */
	public DefaultCrossFunction(Population oldPopulation) {
		this.oldPopulation = (Population) oldPopulation.clone();
	}

	/**
	 * Starts Crossing Over
	 */
	public void generateNewPopulation() {
		if (oldPopulation != null) {
			newPopulation = (Population) oldPopulation.clone();
			Random random = new Random();
			Object tempGene;
			int k = 1, crossPoint = 0;
			double crossOverParameter;
			IChromosome firstChromosome, secondChromosome;
			int oldPopulationSize = oldPopulation.size();
			int chromosomeSize = oldPopulation.getChromosomeSize();
			for (int i = 0; i < oldPopulationSize / 2; i++) {
				crossOverParameter = random.nextDouble();
				if (crossOverParameter < crossOverProbability) {
					System.out.println("cross over");
					firstChromosome = newPopulation.getChromosome(i * 2);
					System.out.println("first "+firstChromosome);
					secondChromosome = newPopulation.getChromosome(i * 2 + 1);
					System.out.println("second "+secondChromosome);
					crossPoint = random.nextInt(chromosomeSize - 1);
					System.out.println("cross point: "+ crossPoint);
					for (int j = crossPoint; j < chromosomeSize; j++) {
						tempGene = firstChromosome.getGene(j);
						System.out.println("----"+tempGene+"----");
						firstChromosome.setGene(j, secondChromosome.getGene(j));
						System.out.println("first >> "+firstChromosome);
						secondChromosome.setGene(j, tempGene);
						System.out.println("second >> "+secondChromosome);
					}
					System.out.println("first "+firstChromosome);
					System.out.println("second "+secondChromosome);
					System.out.println("----");
				}
				k++;
			}
		}
	}
}
