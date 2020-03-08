/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobjectiveprojectschedulingwithModifiedsfla;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Bolarinwa
 */
public class ShuffledFrogLeapingAlgorithm {
    private int populationSize;
    private int numberOfMemplexes;
    private int noOfFrogPerMemplex;
    private int numberOfEvolutionaryIterations;
    private Frog[][] memplex;
    private Frog globalBestFrog;
    private ProjectSchedule project; //instance variable to store the project currently under scrutiny
    
    public ShuffledFrogLeapingAlgorithm(int populationSize, int numOfMemplexes)
    {
        this.populationSize= populationSize;
        this.numberOfMemplexes= numOfMemplexes;
        //this.numberOfEvolutionaryIterations= numberOfEI;
        noOfFrogPerMemplex= this.populationSize/this.numberOfMemplexes;
        this.memplex= new Frog[this.numberOfMemplexes][noOfFrogPerMemplex];
    }
    
    //Method that generate a random population of p frogs
    public Population RandomPopulation(ProjectSchedule projectSchedule) {
		// Initialize population
		Population population = new Population(this.populationSize, projectSchedule);
                this.project= projectSchedule;
		return population;
	}
    
    /* Method that calculates fitness of each frog based on dependencies
     * and employee assignment constraints
    */
    public void calculateFitness(Population population,ProjectSchedule projectSchedule){
        for(Frog frog:population.getIndividuals()){
            double fitness= frog.getDependenciesViolations(projectSchedule.getDSM());
            fitness+=frog.skillMismatches(projectSchedule);
            frog.setFitness(fitness);
            
        }
    }
    
    //Method to get all memplexes
    public Frog[][] getMemplexes(){
        return this.memplex;
    }
    
    //Method to get a particular memplex
    public Frog[] getMemplex(int index){
        return this.memplex[index];
    }
    
    //Method to get the population size
    public int getPopulationSize(){
        return this.populationSize;
    }
    
    //Method to get the number of memplexes
    public int getNumberOfMemplexes(){
        return this.numberOfMemplexes;
    }
    
    //Method to get the number of Frogs per Memplexes
    public int getNumberOfFrogPerMemplex() {
        return this.noOfFrogPerMemplex;
    }
    
    //Function that gets Number of the Evolutionary iteration based on satifying the 
    //termination criteria within each memples
    public int getNumberOfEvolutionaryIterations(){
        return this.numberOfEvolutionaryIterations;
    }
  
    /*Function that sorts the population in descending order of their fitness 
        (the smaller the fitness value the fitter the frog)
    */
    public void sortPopulation(Population population)
        {// Order population by fitness in descending order i.e the last Frog is the fittest
		Arrays.sort(population.getIndividuals(), new Comparator<Frog>() {
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
                this.globalBestFrog= population.getIndividual(0);
	}
    
        //Function that divides the population into m memplexes
    public void divideIntoMemplexes(Population population){
        int index=0;
        Frog individuals[]= population.getIndividuals();
        for(int i=0; i<this.numberOfMemplexes;i++){
            index=i;
            for(int j=0; j<this.noOfFrogPerMemplex;j++){
               this.memplex[i][j]= individuals[index];
               index+=this.numberOfMemplexes;
            }
            
        }
        System.out.println("Global Best Frog:\n "+globalBestFrog.toString());
    }
    
    //Function that gets the number of work packages
    public int getNumberOfWorkPackages() {
        return this.project.getNumberOfWorkPackages();
    }
    
    //Function that improves one frog with another
    public Frog improve(Frog frog1, Frog frog2) {
        Frog Xw= new Frog(frog1); //frog1 is the Worst Frog
           Frog Xb = new Frog(frog2); //frog2 is the Best Frog
           
           //only improve 60% of the frog elements
           int numberOfImprovedElement = (int)(getNumberOfWorkPackages()*0.6);
           int numberOfUnimprovedElement= getNumberOfWorkPackages()- numberOfImprovedElement;
           int [][] temp= new int[numberOfUnimprovedElement][2];
            
            //Get the id of WPs that wont be improved within a frog
           ArrayList<Integer> notImproved = new ArrayList<Integer>();
           
               //Get the unimproved elements
            for(int j =numberOfImprovedElement;j< Xb.getSize();j++) {
               notImproved.add(Xb.getId(j));
            }
           
           //Store the unimproved elements from worst frog (Xw) into temp
                    int index = 0;
                for(int k=0;k<Xw.getSize();k++){
                   if(notImproved.contains(Xw.getId(k)) && index< temp.length) {
                       temp[index][0]= Xw.getId(k);
                       temp[index][1]= Xw.getEmployeeAssignment(k);
                       index++;
                        }
    }
                
                 //Improve  first 60% of Xw with first 60% elements of Xb
            for(int i=0;i<numberOfImprovedElement;i++){
               Xw.setMemes(i, Xb.getMemotype(i));
            }
                
                //Add the unimproved elements back to Xw
            for(int n=numberOfImprovedElement;n<Xw.getSize();n++) {
                    for(int i=0;i<temp.length;){
                        if(notImproved.contains(Xw.getId(n)))
                            i++;
                        else{
               Xw.setMemes(i+numberOfImprovedElement, temp[i]);
               i++;
                        }
                }
            }
           //re-set the fitness of the Xw after improvement with Xb 
           Xw.setFitness(this.project);
           
        return Xw;
    }
    
    //Function that implements the SFLA Local Search (Evolutionary Iterations)
    public void LocalSearch(){
        int totalIterations= 0;
        int memplexNo=0;
        
        //Loop through each memplex
       for(Frog[] currentMemplex : this.getMemplexes()) 
       {
           System.out.println("Analyzing Memplex "+(++memplexNo)+"...");
           
           //Perform Improvement until atleast 90% of Frogs in a Memplex have fitness 0.0
           while(this.isTerminationCriteriaMet(currentMemplex) == false) 
           {
        //Determine Xw (Worst frog),Xb (Best frog)and Xg (Global Best frog)of the currentMemplex
           Frog Xw= this.determineXw(currentMemplex); //Worst Frog
           Frog Xb = this.determineXb(currentMemplex); //Best Frog
           Frog Xg = this.determineXg();
           
           //Check if new frog generated by the best frog is better than the worst frog
           if(Xw.getFitness()> new Frog(improve(Xw,Xb)).getFitness()){
               //When new frog is better than worst frog, replace the worst frog with it
               currentMemplex[positionOfXw(currentMemplex)]= new Frog(improve(Xw,Xb));
           }
           
           //When new frog generated by the best frog is not better than the worst frog
           else { 
               //Check if new frog generated by the global best frog is better than the worst frog
               if (Xw.getFitness()> new Frog(improve(Xw,Xg)).getFitness()) {
               currentMemplex[positionOfXw(currentMemplex)]= new Frog(improve(Xw,Xg));
           }
               //When new frog generated by the global best frog is not better than the worst frog
           else {
               //Replace the Xw (Worst frog) with a randomly generated Frog
                   Frog randomFrog = new Frog(this.project);
                   randomFrog.setFitness(this.project);
               currentMemplex[positionOfXw(currentMemplex)]= randomFrog;
           }
           } //End else
            totalIterations++; //increm,ent number of iterations
           } //End While
        } //End for 
       this.numberOfEvolutionaryIterations= totalIterations;
       System.out.println("--------------------------------------------------------------------------");
       System.out.println();
    }
    
    //Function that determines the Best frog in a memplex
    public Frog determineXb(Frog[] memplex){
        Frog Xb= new Frog(memplex[0]); //initially,set the first frog as the best-fitted frog(Xb)
        //Loop through memplex 
        for(Frog currentFrog : memplex){
            if(currentFrog.getFitness() < Xb.getFitness())
                Xb = new Frog(currentFrog);
        }
        return Xb;
    }
    
    //Function that determines the Worst frog in a memplex
    public Frog determineXw(Frog[] memplex){
        Frog Xw= new Frog(memplex[memplex.length-1]); //initially,set the last frog as the worst-fitted frog(Xw)
        //Loop through memplex 
        for(Frog currentFrog : memplex){
            if(currentFrog.getFitness() > Xw.getFitness())
                Xw = new Frog(currentFrog);
        }
        return Xw;
    }
    
    //Function that determines the Global best frog in a memplex
    public Frog determineXg(){
        return this.globalBestFrog;
    }
    
    //Function that checks if 2 frogs are similar or not
    public boolean isSimilar(Frog frog1, Frog frog2){
        boolean similarityExist = true;
       for(int i= 0; i< getNumberOfWorkPackages();i++){
           if( (frog1.getId(i)!= frog2.getId(i) || 
                   frog1.getEmployeeAssignment(i)!= frog2.getEmployeeAssignment(i) ) 
                            && frog1.getFitness()!= frog2.getFitness())
               similarityExist = false;
       }
        return similarityExist;
    }
    
    //Function that get the position of the currently worst frog
    public int positionOfXw(Frog[] memplex){
        Frog Xw= new Frog(determineXw(memplex));
        int position = -1; //-1 indicate initially not found
        //Loop through memplex
        for(int id=0;id<memplex.length;id++){
            if(isSimilar(memplex[id],Xw)) {
                position= id;
            }
        }
        return position;
    }
    
    //Function that checks termination criteria within each memplex
   public boolean isTerminationCriteriaMet(Frog[] memplex){
       int noFrogWithZeroFitness =0;
       for (Frog currentFrog : memplex){
           if(currentFrog.getFitness()== 0.0)
               noFrogWithZeroFitness++;
       }
       double percentage =((double)(noFrogWithZeroFitness)/memplex.length) * 100;
       return (percentage >= 90); //check if atleast 90% of frog have fitness of 0.0
   }
   
   //Function that shuffles the memplexes after the Local search
   public Population shuffleMemplexes() {
       Population populationCopy = new Population(this.populationSize);
       Frog[][] memplexes = this.getMemplexes();
       int index=0;
       //Loop through  memplexes
       for(int i=0;i<this.getNumberOfMemplexes() & index <this.populationSize;i++){
           for(int j=0;j<this.getNumberOfFrogPerMemplex();j++){
               populationCopy.setIndividuals(index++,  memplexes[i][j]);;
           }
       }
       this.sortPopulation(populationCopy);
       return populationCopy;
   }
}

