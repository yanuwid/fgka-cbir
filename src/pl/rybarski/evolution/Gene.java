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

/** Gene
 * @author Janusz Rybarski
 * e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */

public class Gene implements Cloneable{
    
    /**
     * Array of possibe gene value
     */
    Object[] geneTypes;
    /**
     * Value of the gene
     */
    Object gene =new Object();
    /**
     * Mutation propability for gene
     */
    double mutationProbability;
    /**
     * Reference to function used for create <I>String</I> representation of gene
     */
    IGeneToString toStringFunction;
    
    /**
     * Creates a new instance of Gene
     * @param geneTypes Array of gene types
     * @param toStringFunction Reference to GeneToStringFunction
     */
    
    public Gene(Object[] geneTypes, IGeneToString toStringFunction) {
        this.toStringFunction = toStringFunction;
        this.geneTypes = geneTypes;
        Random random = new Random();                         //generate random gene value
        int index = random.nextInt(geneTypes.length);
        gene = geneTypes[index];;
    }
    
    /**
     * Set mutation propability
     * @param mutationProbability Set propability of the gene
     */
    public void setMutationProbability(double mutationProbability){
        this.mutationProbability = mutationProbability;
    }
   
    /**
     * Return mutation propability
     * @return mutation propability
     */
    public double getMutationProbability(){
        return this.mutationProbability;
    }
    
    /**
     * Set gene value with Object. Array of gene types must contain element
     * @param value New gene value
     */
    public void setGene(Object value){
        int length = geneTypes.length;
        for (int i=0; i< length; i++){
            if(geneTypes[i].equals(value)){
                gene = value;
            }
        }
    }
    
    /**
     * Set gene value for specified type from list of types
     * @param index Index of gene type
     */
    public void setGeneValue(int index){
        gene = geneTypes[index];
    }
    
    /**
     * Return array of gene types
     * @return Array of gene types
     */
    public Object[] getGeneTypes(){
        return this.geneTypes;
    }
    
    /**
     * Return gene value
     * @return Gene value
     */
    public Object getGeneValue(){
        return this.gene;
    }
    
    /**
     * Return number of gene types.
     * @return Number of gene types
     */
    public int getNumberOfTypes(){
        return geneTypes.length;
    }
    
    /**
     * Mutate the gene. Return <I>1</I> if everything is Ok, <I>0</I> otherwise
     * @return  Return <I>1</I> if everything is Ok, <I>0</I> otherwise
     */
    public boolean mutation(){
        Random random = new Random();
        double mutationParametr = random.nextDouble();
        //System.out.println("mutation parameter "+mutationParametr+" \nprobability:"+ mutationProbability);
        if(mutationParametr  < mutationProbability){
            int index = random.nextInt(geneTypes.length);
            gene = geneTypes[index];
            return true;
        }
        return false;
    }
    
    /**
     * Return <I>String</I> representation of the gene using <I>GeneToStingFunction
     * </I>. If no GeneToStringFunction was set, function return String using <I>toString</I>
     * standard function.
     * @return Return String representation of the gene
     * @see IGeneToString
     */
    public String toString(){
        if (toStringFunction == null)
            return gene.toString();
        else 
            return toStringFunction.toString(gene);
    }

    /**
     * Set function used for generating <I>String</I> representaion
     * of the gene
     * @param toStringFunction Reference to function
     */
    public void setToStringFunction(IGeneToString toStringFunction) {
        this.toStringFunction = toStringFunction;
    }

    /**
     * Return reference to function used for generating <I>String</I> representaion
     * of the gene
     * @return Return reference to function GeneToString
     */
    public IGeneToString getToStringFunction() {
        return toStringFunction;
    }
    
    /**
     * Create and return a copy of this gene
     * @return copy of the gene
     */
    public Object clone(){
       try{
           Gene geneCopy = (Gene)super.clone();
           geneCopy.geneTypes = (Object[])geneTypes.clone();
           geneCopy.mutationProbability = mutationProbability;
           geneCopy.toStringFunction = toStringFunction;
           try {
                gene =  (getClass().cast(gene)).clone();
           } catch (Exception e) {
                gene = null;
           }
           
           return geneCopy;
        }
        catch (CloneNotSupportedException e ){
            return null;
        }
    }
}
