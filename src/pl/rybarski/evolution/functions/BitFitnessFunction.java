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

import pl.rybarski.evolution.*;

/**
 * Bit fitness function
 * @author Janusz Rybarski
 * e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */
public class BitFitnessFunction implements IFitnessFunction{
    
    /**
     * Return fitness value for specified chromosome. Gene in chromosome must be <I>boolean</I>
     * type. Fitness value is calculated according to the function: <I>y = y + i ^2</I> for i=0 to 
     * size of chromosome where: <BR>
     * <I>y </I> fitness function value <BR>
     * <I>i</I> position of gene in chromosome
     * @param chromosome Reference to chromosome
     * @return Value of the fitness function
     */
    public double getValue(IChromosome chromosome) {
        double fitnessValue = 0;
        System.out.println(chromosome);
        int chromosomeSize = chromosome.size();
        for(int i = 0 ; i< chromosomeSize; i++){
            if ((java.lang.Boolean)chromosome.getGene(i)){
                fitnessValue += i * 2;                    
            }
        }  
        return fitnessValue;
    }
}
