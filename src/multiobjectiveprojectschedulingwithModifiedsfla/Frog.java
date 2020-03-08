/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobjectiveprojectschedulingwithModifiedsfla;

import java.util.ArrayList;

/**
 *
 * @author Bolarinwa
 */
public final class Frog {
    private double fitness= -1;
    private int[][] memes;
    
    //constructor
    public Frog(ProjectSchedule project){
        int numWp= project.getNumberOfWorkPackages();
        this.memes= new int[numWp][2];
        
        int count=0;
        //Add Random WorkPackage Ids
        while(count<numWp){
            for(int id:project.getRandomWorkPackageId()) {
                this.memes[count++][0]=id;
            }
        }
        
       
        //Add Random Employee Assignment
        count=0;
        while(count<numWp){
            for(int assignment:project.getRandomEmployeeAssignment()) {
                this.memes[count++][1]=assignment;
            }
        }
        
    }
    
    //Clone a frog with this constructor
    public Frog(Frog frog) {
         int numWp= frog.getSize();
        this.memes= new int[numWp][2];
        for (int i=0;i< frog.getSize();i++){
            this.setMemes(i, frog.getMemotype(i));
        }
        //this.elements= frog.getElements();
        this.fitness= frog.getFitness();
    }
    
    //set Frog's fitness
    public void setFitness(double fitness){
        this.fitness= fitness;
    }
    
    
    public void setFitness(ProjectSchedule project){
        double fitness= this.getDependenciesViolations(project.getDSM());
            fitness+=skillMismatches(project);
            this.setFitness(fitness);
    }
    
    //get Frog's fitnes
    public double getFitness(){
        return this.fitness;
    }
    
     public void setMemes(int offset,int a[]){
            this.memes[offset]= a;
        }
    
    
    //Get Frog's elements
    public int[][] getMemes(){
        return this.memes;
    }
    
    //Get Frog's element
    public int[] getMemotype(int index){
        return this.memes[index];
    }
    
    //get  WP's id at location 'position' in Frog
    public int getId(int position) {
        return this.memes[position][0];
    }
    
     //get  WP's employee assignment at location 'position' in Frog
    public int getEmployeeAssignment(int position){
        return this.memes[position][1];
    }
    
    //get Frog's size
    public int getSize(){
        return this.memes.length;
    }
    
      @Override
    public String toString(){
        String str = "\n";
        for(int i=0;i<this.getSize();i++){
            str+=this.getId(i)+" "+this.getEmployeeAssignment(i)+"\n";
        }
        str+="Fitness: "+this.getFitness();
        return str;
    }
    
    //get number of dependency/precedence violation in Frog
     public int getDependenciesViolations(int[][] DSM){
        int numViolation= this.memes.length;
        int noViol=0;
        try{
        for(int i=0;i<this.memes.length;i++){
            noViol=0;
            int k= (this.memes[i][0])-1;
            
               if(DSM[k].length==0) {
                --numViolation;
               }
               else{
            for(int m=0;m<DSM[k].length;m++) {
                
                for(int n=0;n<i;n++){
                    if(this.memes[n][0] == DSM[k][m])
                        ++noViol;
                }
            }
            if (noViol==DSM[k].length)
                --numViolation;
               }
            
        }
        
      
        }
        catch(Exception ex){
        ex.printStackTrace();
        }
         return numViolation;
    }
     
     //Method that calculates wrong no of employee assignment
    public int skillMismatches(ProjectSchedule projectSchedule){
        int totalMismatches= 0;
        int misMatches=0;
        
        boolean match=false;
        ArrayList<Integer> availableCompetences = new ArrayList<Integer>();
        ArrayList<Integer> totalCompetences = new ArrayList<Integer>();
       
            //Loop through each elements of a given frog to get info about the WPs
            for(int i=0;i<this.getSize();i++){
                
                Skill wpCompetence[]= projectSchedule.getWorkPackage(
                        this.getId(i)).getWpRequiredCompetences();
                
            ArrayList<Integer> employeeAss = bitPositions(this.getEmployeeAssignment(i),
                    projectSchedule.getNumberOfEmployees());
            
            totalCompetences = new ArrayList<Integer>();
            
            //Loop through employee assigned
                for(int k:employeeAss)
                {
                    match= false;
                    availableCompetences = new ArrayList<Integer>();
                    for(int m =0;m< projectSchedule.getEmployee(k).getNumberOfSkill();m++) {
                    availableCompetences.add(projectSchedule.getEmployee(k).getSkill(m).getSkillId());
                    
                    }
                    totalCompetences.addAll(availableCompetences);
                    
            //Loop through the current Wp's required competences
                    for (Skill j:wpCompetence){
                         if(availableCompetences.contains(j.getSkillId())){
                            match=true;
                         }
                    }
                    //check if there was no required competence(s) match
                    if(!match) {
                        misMatches++;
                    }
                    
                }
                for(Skill j:wpCompetence) {
                    if (!totalCompetences.contains(j.getSkillId()))
                         misMatches++;
                }
                
                
            }
           
        return misMatches;
    }
    
    //Function that convert the binary representation of staff allocation to readable form
    public ArrayList<Integer> bitPositions(int numb,int numEmp){
        String binaryEq= String.format("%"+numEmp+"s",Integer.toBinaryString(numb)).replace(' ','0');
        ArrayList<Integer> integerList = new ArrayList<>();
        for(int i=0;i<binaryEq.length();i++){
        if(binaryEq.charAt(i)=='1'){
            integerList.add(i+1);
        }
        }
        return integerList;
    }
}
