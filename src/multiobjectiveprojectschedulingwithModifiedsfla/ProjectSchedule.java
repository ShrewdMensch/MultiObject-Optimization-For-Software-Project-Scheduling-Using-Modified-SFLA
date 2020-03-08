/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobjectiveprojectschedulingwithModifiedsfla;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 *
 * @author Bolarinwa
 */
public class ProjectSchedule {
    private final  HashMap<Integer,Employee> employees;
    private final HashMap<Integer, WorkPackage> workPackages;
    private int[][] DSM; //Design Structure Matrix for dependencies
    
    //Default constructor
    public ProjectSchedule(){
        this.employees= new HashMap<Integer, Employee>();
        this.workPackages= new HashMap<Integer, WorkPackage>();
        DSM= new int[][]{};
    }
    
    //clone a project schedule
    public ProjectSchedule(ProjectSchedule cloneable){
        this.employees= cloneable.getEmployees();
        this.workPackages= cloneable.getWorkPackages();
        DSM= new int[][]{};
    }
    
    public HashMap<Integer,Employee> getEmployees(){
        return this.employees;
    }
    
     public HashMap<Integer,WorkPackage> getWorkPackages(){
        return this.workPackages;
    }
     
     public void addEmployee(int id, String name,Skill[] skillSet){
        this.employees.put(id,new Employee(id,name,skillSet) );
        //numberOfEmployees++;
     }
     
     public void addWorkPackage(int id,String WpName,Skill[] WpRequiredCompetences){
         this.workPackages.put(id,new WorkPackage(id,WpName,WpRequiredCompetences));
         //this.numberOfWorkpackages++;
     }
     
     public void addDSM(int DSM[][]){
         this.DSM= DSM;
     }
     
     public int[][] getDSM(){
         return this.DSM;
     }
     
     public Employee getEmployee(int id){
         return (Employee)this.employees.get(id);
     }
     
     public WorkPackage getWorkPackage(int id) {
         return (WorkPackage)this.getWorkPackages().get(id);
     }
     
     public int getNumberOfWorkPackages() {
         return this.workPackages.size();
     }
     
     public int getNumberOfEmployees(){
         return this.employees.size();
     }
     
     public ArrayList<Integer> getRandomWorkPackageId(){
         return getRandomNonRepeatingIntegers(this.getNumberOfWorkPackages()
                 ,1,getNumberOfWorkPackages());
     }
     
     public ArrayList<Integer> getRandomEmployeeAssignment(){
         int numEmployee= this.getNumberOfEmployees();
         int max= (int)(Math.pow(2, numEmployee)-1);
         return getRandomNonRepeatingIntegers(this.getNumberOfWorkPackages()
                 ,1,max);
     }
     
     private int getRandomInt(int min, int max) {
    Random random = new Random();

    return random.nextInt((max - min) + 1) + min;
}

private ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min,
        int max) {
    ArrayList<Integer> numbers = new ArrayList<Integer>();

    while (numbers.size() < size) {
        int random = getRandomInt(min, max);

        if (!numbers.contains(random)) 
            numbers.add(random);
        
    }

    return numbers;
}
}
