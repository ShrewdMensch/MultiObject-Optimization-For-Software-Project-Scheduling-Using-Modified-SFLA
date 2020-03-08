/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobjectiveprojectschedulingwithModifiedsfla;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Bolarinwa
 */
public class Population {
    private Frog individual[];
    private double cummlativeFitness=-1;
    
    //Initialize blank population of Frogs
    public Population(int populationSize){
        this.individual= new Frog[populationSize];
    }
    
    /**
     * Initializes population of individuals
     * 
     * @param populationSize The size of the population
     * @param project The project schedule information
     */
    public Population(int populationSize, ProjectSchedule project){
        this.individual= new Frog[populationSize];
        // Loop over population size
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			// Create individual
			Frog individual = new Frog(project);
			// Add individual to population
			this.individual[individualCount] = individual;
		}
    }
    
    public void setIndividuals(int offset,Frog frog){
        this.individual[offset]= frog;
    }
    
    public Frog[] getIndividuals(){
        return this.individual;
    }
    
    /**
	 * Get Frog at offset
	 * 
	 * @param offset
	 * @return individual
	 */
	public Frog getIndividual(int offset) {
		return this.individual[offset];
	}
    
        public int Size(){
            return this.individual.length;
        }
        
    public Frog getFittest(int offset) {
		// Order population by fitness in descending order i.e the last Frog is the fittest
		Arrays.sort(this.individual, new Comparator<Frog>() {
			@Override
			public int compare(Frog o1, Frog o2) {
				if (o1.getFitness() < o2.getFitness()) {
					return -1;
				} else if (o1.getFitness() > o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});

		// Return the fittest individual
		return this.individual[offset];
	}
}
