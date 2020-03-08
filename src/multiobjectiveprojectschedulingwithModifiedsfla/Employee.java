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
public class Employee {
    private String name;
    private int id;
    private Skill[] skillSet;
    
    public Employee(){
        name= new String();
        id= -1;
        skillSet= new Skill[]{};
    }
    
    public Employee(int id, String name,Skill[] skillSet){
        this.id= id;
        this.name= name;
        this.skillSet= skillSet;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Skill[] getSkills(){
        return this.skillSet;
    }
    
    public Skill getSkill(int id){
        return this.skillSet[id];
    }
    
    public String getSkillsAsString(){
        String skillString= "Skill: ";
        for(int index=0; index<skillSet.length-1;index++){
            skillString+=skillSet[index].getSkillName()+", ";
        }
        //Print the Last Skill separately so as to omit the comma
        skillString+=skillSet[skillSet.length-1].getSkillName(); 
        return skillString;
    }
    
    public int getNumberOfSkill(){
        return this.skillSet.length;
    }
}
