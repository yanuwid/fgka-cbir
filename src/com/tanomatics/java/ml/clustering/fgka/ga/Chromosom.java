package com.tanomatics.java.ml.clustering.fgka.ga;

import com.tanomatics.java.ml.clustering.Centroids;
import com.tanomatics.java.ml.core.Dataset;
import com.tanomatics.java.ml.tools.DoubleFormatter;

import pl.rybarski.evolution.IChromosome;
import pl.rybarski.evolution.IGeneToString;


/**
 * A class representing a set solutions 
 * @author yanuwid
 * @version 1.0
 */
public class Chromosom implements IChromosome, Cloneable {

	private Object[] geneList;

	private double crossOverProbability = 1;

	private double mutationProbability = 1;

	private int chromosomeSize;
	
	private double TWCV;
	
	private Centroids centroid;

	private double eZ;

	private int K;
	
	public Chromosom(int chromosomeSize,
					 IGeneToString toStringFunction, 
					 Object[] geneTypes,
					 Dataset dataset){
		
		this.chromosomeSize = chromosomeSize;
		this.K = geneTypes.length;
		
		/* testing
		geneList = new Object[chromosomeSize];
		geneList[0] = new Gene(geneTypes,0,toStringFunction);
		geneList[1] = new Gene(geneTypes,0,toStringFunction);
		geneList[2] = new Gene(geneTypes,0,toStringFunction);
		geneList[3] = new Gene(geneTypes,0,toStringFunction);
		
		geneList[4] = new Gene(geneTypes,1,toStringFunction);
		geneList[5] = new Gene(geneTypes,1,toStringFunction);
		geneList[6] = new Gene(geneTypes,1,toStringFunction);
		geneList[7] = new Gene(geneTypes,1,toStringFunction);
		
		geneList[8] = new Gene(geneTypes,2,toStringFunction);
		geneList[9] = new Gene(geneTypes,2,toStringFunction);
		geneList[10] = new Gene(geneTypes,2,toStringFunction);
		geneList[11] = new Gene(geneTypes,2,toStringFunction);
		*/
		// end of testing
		
		
		geneList = new Object[chromosomeSize];		
		for (int i = 0; i < chromosomeSize; i++){
			geneList[i] = new Gene(geneTypes, toStringFunction);
		    
			Gene tmpGen = (Gene) geneList[i];
		    tmpGen.setMutationProbability(mutationProbability);
		}
		
		setCentroid(dataset,this,K);

	}
	
	/**
	 * set centroid from chromosom
	 * @param dataset
	 * @param chromosom
	 * @param K
	 */
	public void setCentroid(Dataset dataset, Chromosom chromosom, int K){
		centroid = new Centroids(dataset, this, K);
		TWCV = centroid.getTWCV();
	}
	
	/**
	 * get TWCV
	 * @return TWCV
	 */
	public double getTWCV(){
		return this.TWCV;
	}
	
	/**
	 * get centroid
	 * @return the centroid
	 */
	public Centroids getCentroid(){
		return this.centroid;
	}

	public boolean geneMutation(int geneNumber) {
		return ((Gene) geneList[geneNumber]).mutation();
	}

	public double getCrossOverProbability() {
		return this.crossOverProbability;
	}
	
	public Object getGene(int geneNumber) {
		return geneList[geneNumber];
	}
	
	public int getGen(int geneNumber){
		return Integer.valueOf(geneList[geneNumber].toString());
	}

	public double getMutationProbability() {
		return this.mutationProbability;
	}

	public void setCrossOverProbability(double crossOverProbality) {
		this.crossOverProbability = crossOverProbality;
	}

	public void setGene(int geneNumber, Object value) {
		geneList[geneNumber] = value;
		
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public int size() {
		return geneList.length;
	}

	/**
	 * checking the legality of a set of solution
	 */
	public boolean isLegal(){
		int n[] = new int[K];
		for(int i = 0; i < size(); i++)
			n[getGen(i)]++;
		
		float e = 0;
		for (int i = 0; i < K; i++)
			if(n[i] !=0 ) e++;
		
		eZ = e/K;
		
		n = null;
		
		if(eZ == 1) return true;
		else return false;	
	}
	
	/**
	 * get legality ratio
	 * @return the legality Ratio
	 */
	public double getLegalityRatio(){
		return this.eZ;
	}
	
	public boolean equals(){
		return false;
		
	}
	
	public Object clone(){
		Chromosom chromosomeCopy = null ;
		try {
			chromosomeCopy =  (Chromosom) super.clone();
			chromosomeCopy.chromosomeSize = chromosomeSize;
			chromosomeCopy.crossOverProbability = crossOverProbability;
			chromosomeCopy.mutationProbability = mutationProbability;
			chromosomeCopy.geneList = (Object[]) geneList.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return chromosomeCopy;
	}
	
	/**
	 * chromosom string representation
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < chromosomeSize; i++) {
			sb.append(geneList[i]);
		}
		sb.append(" ");
		sb.append("size="+ size() + " ");
		sb.append("twcv="+ DoubleFormatter.customDecimalFormat(getTWCV()));
		
		return sb.toString();
	}

}
