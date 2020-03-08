/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobjectiveprojectschedulingwithModifiedsfla;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Bolarinwa
 */
public class MultiObjectiveProjectSchedulingWithModifiedSFLA {

    public static void main(String[] args) 
    {
        //Get a ProjectSchedule object with all the available information about the Project.
        ProjectSchedule project1= initializeProject();
        
        //Create an instance of the ShuffledFrogLeapingAlgorithm to Optimize project1
        ShuffledFrogLeapingAlgorithm SFLA = new ShuffledFrogLeapingAlgorithm(200,5);
        
        //Generate random population of P frogs
        Population population = SFLA.RandomPopulation(project1);
        
        //Calculate fitness of each individual frogs in the population
        SFLA.calculateFitness(population, project1);
        
        //Sort the population in descending order
        SFLA.sortPopulation(population);
        
        //Printing list of the whole population before applying SFLA
            System.out.println("Population initially: ");
            int i=0;
            for(Frog frog:population.getIndividuals()) {
                System.out.println("frog "+(++i));
            System.out.println(frog.toString());
            System.out.println();
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------");
            System.out.println();
        
        //Divide population p into m memplexes
        SFLA.divideIntoMemplexes(population);
        
        //Perform Local Search (Evolutionary Iteration)
        SFLA.LocalSearch();
            
           
        //Shuffle the Memplexes
        population= SFLA.shuffleMemplexes();
            
            //Printing list of the whole population after applying SFLA
            System.out.println("Population After Local Search and Shuffling: ");
            int id=0;
            for(Frog frog:population.getIndividuals()) {
                System.out.println("frog "+(++id));
            System.out.println(frog.toString());
            System.out.println();
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------");
            
            //Get and Print the Best Solution
            Frog bestSolution=population.getFittest(0);
        System.out.println();
        System.out.println("Best Solution found after "+
        String.format("%,d",SFLA.getNumberOfEvolutionaryIterations())+" iterations: ");
        System.out.println(bestSolution.toString());
        System.out.println();
        System.out.println("Best Solution Fitness: "+bestSolution.getFitness());
        System.out.println("Number of Dependecy violations :"+
                bestSolution.getDependenciesViolations(project1.getDSM()));
        System.out.println("Number of skill-mismatched Staff allocation: "+
                bestSolution.skillMismatches(project1));
        System.out.println();
        
        //Loop through the WP Orderings of the Best Frog(Solution)
        for(int index=0; index < bestSolution.getSize();index++){
            //Print Details of each WP
            System.out.printf("Work Package %3d: ",(index+1));
            System.out.println(project1.getWorkPackage(bestSolution.getId(index)).getWpName());
            
            //Print the given workpackage's required competences
            System.out.println(project1.getWorkPackage(bestSolution.getId(index))
                    .getRequiredCompetencesAsString());
            System.out.println();
            
            //Print the Staff allocation for the given WP
            System.out.println("Staff Allocated:");
            for(int employeeId : bitPositions(bestSolution.getEmployeeAssignment(index)
                    ,project1.getNumberOfEmployees())) {
                System.out.println("Staff Id: "+project1.getEmployee(employeeId).getId());
                System.out.println("Name: "+project1.getEmployee(employeeId).getName());
                //Print the Skills 
                System.out.println(project1.getEmployee(employeeId).getSkillsAsString());
                System.out.println();
            }
            System.out.println("--------------------------------------------------------------------------");
        }
            System.out.println("--------------------------------------------------------------------------");
            
    }
    
    //Function that convert the binary representation of staff allocation to readable form
    public static ArrayList<Integer> bitPositions(int numb,int numEmp){
        String binaryEq= String.format("%"+numEmp+"s",Integer.toBinaryString(numb)).replace(' ','0');
        ArrayList<Integer> integerList = new ArrayList<>();
        for(int i=0;i<binaryEq.length();i++){
        if(binaryEq.charAt(i)=='1'){
            integerList.add(i+1);
        }
        }
        return integerList;
    }
    
    //Fuction to initialize a given project's info
    public static ProjectSchedule initializeProject() {
        ProjectSchedule project= new ProjectSchedule();
        
        //Define Skils
        Skill skill1= new Skill(1,"Programming");
        Skill skill2= new Skill(2,"Object Oriented Design");
        Skill skill3= new Skill(3,"System Analysis");
        Skill skill4= new Skill(4,"GUI Designing");
        Skill skill5= new Skill(5,"Database MGT");
        
        
        //Add Employees
        project.addEmployee(1, "Abdulazeez", new Skill[]{skill1,skill2,skill4});
        project.addEmployee(2, "Abdulganiyyu", new Skill[]{skill1,skill2});
        project.addEmployee(3, "Abdulhammeed", new Skill[]{skill3,skill5});
        //project.addEmployee(4, "Bolarinwa", new Skill[]{skill1,skill2,skill3,skill4});
        //project.addEmployee(5, "Sodiq", new Skill[]{skill1,skill3,skill5});
        
        
        //Add WorkPackages
         project.addWorkPackage(1, "Feasibility Study",new Skill[]{skill3});
        project.addWorkPackage(2, "Requirement Analysis",new Skill[]{skill1,skill3,skill4});
        project.addWorkPackage(3, "Design Database",new Skill[]{skill1,skill3,skill5});
        project.addWorkPackage(4, "Design GUI",new Skill[]{skill1,skill4});
        project.addWorkPackage(5, "Coding",new Skill[]{skill1});
        project.addWorkPackage(6, "Write user manual",new Skill[]{skill1,skill3});
        project.addWorkPackage(7, "Testing",new Skill[]{skill1,skill2,skill3});
        
        //project.addWorkPackage(6, "Testing",new Skill[]{skill1,skill2});
       // project.addWorkPackage(7, "Testing",new Skill[]{skill1,skill4});
       // project.addWorkPackage(8, "Testing",new Skill[]{skill1,skill2,skill4});
        //project.addWorkPackage(9, "Testing",new Skill[]{skill1,skill2,skill3});
        //project.addWorkPackage(10, "Testing",new Skill[]{skill4});
            
        //Add DSM for dependecies
        project.addDSM(new int[][]{{},{1},{1,2},{1,2},{1,2,3,4},{5},{5}});
        //project.addDSM(new int[][]{{},{},{2},{},{1,3},{},{},{6},{6},{8}});
        return project;
    }

}
