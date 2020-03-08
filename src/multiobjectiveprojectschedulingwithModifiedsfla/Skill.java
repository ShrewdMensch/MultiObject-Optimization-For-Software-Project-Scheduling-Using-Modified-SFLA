/*
 *This class models a skill employee could be gifted with
 */
package multiobjectiveprojectschedulingwithModifiedsfla;

/**
 *
 * @author Bolarinwa
 */
public class Skill {
    private String skillName;
    private int skillId;
    
    //Default Constructor
    public Skill(){
        skillName= new String();
        skillId= -1;
    }
    
    //Constructor with 2 parameters
public Skill(int skillId,String skillName){
    this.skillName= skillName;
    this.skillId= skillId;
}    

public String getSkillName(){
    return this.skillName;
}

public int getSkillId(){
    return this.skillId;
}

}
