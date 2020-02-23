import java.util.Random;
import java.util.Scanner;

public class Simulation {
    public static Random mRandom;

    public static void main(String[] args) {
        int seedValue, numFloors, numElevators;

        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter a seed value: ");
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

        Building kyleCorp = new Building(numFloors, numElevators);

        int userNum = -1;
        int stepNum = 1;
        while(userNum != 0){
            System.out.print("Simulate how many steps? ");
            userNum = sc.nextInt();

            for(int i = 0; i < userNum; i++){
                System.out.println("Step " + stepNum);
                kyleCorp.tick();
                stepNum++;
            }
        }
    }
}
