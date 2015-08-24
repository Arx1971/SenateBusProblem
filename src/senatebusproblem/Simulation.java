package senatebusproblem;

import java.util.Scanner;

/**
 *
 * @author Rajind
 */
public class Simulation {
    
    private static final SharedData sharedData = new SharedData();
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);  
        System.out.print("Enter the number of Riders : ");  
        int rider_count = scanner.nextInt();
        System.out.println("");
        
        new Bus(sharedData).busActivity();
                
        for(int i=0; i < rider_count ; i++){
            new Rider(sharedData).start();
        }
    }
}
