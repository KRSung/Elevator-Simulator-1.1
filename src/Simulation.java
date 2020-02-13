import java.util.Random;
import java.util.Scanner;

public class Simulation {
    static Random mRandom;

    public static void main(String[] args) {
        int userNum = -1;
        int seedValue, numFloors, numElevators;
        int stepNum = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a seed value: ");
        while(!sc.hasNextInt()){
            System.out.println("Error value must be and integer");
            System.out.println("Enter a seed value: ");
            sc.next();
        }
        seedValue = sc.nextInt();
        System.out.print("How many floors? ");
        numFloors = sc.nextInt();
        System.out.print("How many elevators? ");
        numElevators = sc.nextInt();

        Random rand = new Random(seedValue);
        Simulation.mRandom = rand;
        System.out.println(mRandom);

        Building kyleCorp = new Building(numFloors, numElevators);

//        while(userNum != 0){
//            System.out.println("Simulate how many steps? ");
//            System.out.println("Step " + stepNum);
//        }

    }
}
