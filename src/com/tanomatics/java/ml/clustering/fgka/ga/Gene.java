package com.tanomatics.java.ml.clustering.fgka.ga;

import java.util.Random;

import pl.rybarski.evolution.IGeneToString;

/**
 * A class for representing solution for each dataset
 * @author yanuwid
 * @version 1.0
 */
public class Gene implements Cloneable{
    
    Object[] geneTypes;
    Object gene =new Object();
    double mutationProbability;
    IGeneToString toStringFunction;
    
    public Gene(Object[] geneTypes, IGeneToString toStringFunction) {
        this.toStringFunction = toStringFunction;
        this.geneTypes = geneTypes;
        Random random = new Random();
        int index = random.nextInt(geneTypes.length);
        gene = geneTypes[index];;
        random = null;
    }
    
    public Gene(Object[] geneTypes, int index,IGeneToString toStringFunction) {
        this.toStringFunction = toStringFunction;
        this.geneTypes = geneTypes;
        gene = geneTypes[index];;
    }
    
    public void setMutationProbability(double mutationProbability){
        this.mutationProbability = mutationProbability;
    }
   
    public double getMutationProbability(){
        return this.mutationProbability;
    }
    
    public void setGene(Object value){
        int length = geneTypes.length;
        for (int i=0; i< length; i++){
            if(geneTypes[i].equals(value)){
                gene = value;
            }
        }
    }
    

    public void setGeneValue(int index){
        gene = geneTypes[index];
    }
    

    public Object[] getGeneTypes(){
        return this.geneTypes;
    }
    

    public Object getGeneValue(){
        return this.gene;
    }
    
 
    public int getNumberOfTypes(){
        return geneTypes.length;
    }
    

    public boolean mutation(){
        Random random = new Random();
        double mutationParametr = random.nextDouble();
        if(mutationParametr  < mutationProbability){
            int index = random.nextInt(geneTypes.length);
            gene = geneTypes[index];
            random = null;
            return true;
        }
        random = null;
        return false;
    }
    

    public String toString(){
        if (toStringFunction == null)
            return gene.toString();
        else 
            return toStringFunction.toString(gene);
    }


    public void setToStringFunction(IGeneToString toStringFunction) {
        this.toStringFunction = toStringFunction;
    }


    public IGeneToString getToStringFunction() {
        return toStringFunction;
    }
    
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