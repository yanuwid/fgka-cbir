package pl.rybarski.evolution;


public interface IChromosome {

	/**
	 * Return copy of the chromosome
	 * 
	 * @return Clone of chromosome
	 */
	public abstract Object clone();

	/**
	 * Indicates whether some other object is "equal to" this network.
	 * 
	 * @param obj
	 *            the reference object to compare with
	 * @return <I>true</I> if this chromosome is the same as the obj argument;<I>
	 *         false</I> otherwise.
	 */
	public abstract boolean equals(Object obj);

	/**
	 * Mutant gene specified by genNumber. <BR>
	 * Return <I>1</I> if gene was mutaded, <I>0</I> otehrwise
	 * 
	 * @return information about mutation
	 * @param geneNumber
	 *            gene number
	 */
	public abstract boolean geneMutation(int geneNumber);

	/**
	 * Return size of the chromosome
	 * 
	 * @return Size of the chromosome
	 */
	public abstract int size();

	/**
	 * Return cross over propability
	 * 
	 * @return cross over propability
	 */
	public abstract double getCrossOverProbability();

	/**
	 * Return gene for with specified gene NUmber
	 * 
	 * @return Gene
	 * @param geneNumber
	 *            Gene number
	 */
	public abstract Object getGene(int geneNumber);

	/**
	 * Return mutation propability
	 * 
	 * @return Mutation propability
	 */
	public abstract double getMutationProbability();

	/**
	 * Set propability of cross over
	 * 
	 * @param crossOverProbality
	 *            cross over propability
	 */
	public abstract void setCrossOverProbability(double crossOverProbality);

	/**
	 * Set gene with specified value. Value must be type of boolean
	 * 
	 * @param geneNumber
	 *            Gene number
	 * @param value
	 *            Gene value
	 */
	public abstract void setGene(int geneNumber, Object value);

	/**
	 * Set mutation probability
	 * 
	 * @param mutationProbability
	 *            Mutation propability
	 */
	public abstract void setMutationProbability(double mutationProbability);

	/**
	 * Return string representation of chromosome where <BR>
	 * <I>1</I> - represent <I>true</I><BR>
	 * <I>2</I> - represent <I>false</I>
	 * 
	 * @return String representation of chromosome
	 */
	public abstract String toString();

}