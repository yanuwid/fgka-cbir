package com.tanomatics.java.ml.clustering.fgka.ga;

import pl.rybarski.evolution.IGeneToString;

/**
 * GeneToString is function to create <I>String</I> representation of the gene <BR>
 * @author yanuwid
 * @version 1.0
 */
public class GeneToString implements IGeneToString{
    
    public GeneToString() {
    }
    
    /**
     * Returns <I>String</I> representation of the gene. 
     * @param geneType gene type
     * @return the gene String 
     */
    public String toString(Object geneType){
    	return Integer.toString((Integer)geneType);
    }
}
