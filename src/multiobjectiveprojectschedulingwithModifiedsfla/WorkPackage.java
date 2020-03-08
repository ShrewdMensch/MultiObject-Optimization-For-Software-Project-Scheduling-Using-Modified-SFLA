/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobjectiveprojectschedulingwithModifiedsfla;

/**
 *
 * @author Bolarinwa
 */
public class WorkPackage {
   private int workPackageId;
   private String workPackageName;
   private Skill[] requiredCompetences;
    
    public WorkPackage(){
        this.workPackageId= -1;
        this.workPackageName= new String();
        this.requiredCompetences= new Skill[]{};
    }
    
    public WorkPackage(int id ,String workPackageId, Skill[] requiredCompetences){
        this.workPackageId= -1;
        this.workPackageName= workPackageId;
        this.requiredCompetences= requiredCompetences;    
    }
    
    public int getWpId(){
        return this.workPackageId;
    }
    
    public String getWpName(){
        return this.workPackageName;
    }
    
    public Skill[] getWpRequiredCompetences() {
        return this.requiredCompetences;    }
    
    public Skill getWpRequiredCompetence(int id){
        return this.requiredCompetences[id];
    }
    
    public int getNumberOfWpRequiredCompetences(){
        return this.requiredCompetences.length;
    }
    
    public String getRequiredCompetencesAsString(){
        String skillString= "Required Competence(s): ";
        for(int index=0; index<requiredCompetences.length-1;index++){
            skillString+=requiredCompetences[index].getSkillName()+", ";
        }
        //Print the Last required competence separately so as to omit the comma
        skillString+=requiredCompetences[requiredCompetences.length-1].getSkillName();
        return skillString;
    } 
}
