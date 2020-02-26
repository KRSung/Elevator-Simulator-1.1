import java.util.*;
//import java.lang.*;

public class Building {
    private ArrayList<ArrayList<Integer>> allFloors = new ArrayList<>();
    private ArrayList<Elevator> elevatorArrayList = new ArrayList<>();
    private int numFloors, numElevators;

    // constructs a building with the user inputting the amount of floors and elevators in the building.
    public Building(int numFloors, int numElevators){
        this.numFloors = numFloors;
        this.numElevators = numElevators;
        for (int i = 0; i < numFloors; i++){
            this.allFloors.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < numElevators; i++){
            this.elevatorArrayList.add(new Elevator(i + 1 , this));
        }
    }

//    returns the array list of a floors passengers
    public ArrayList<Integer> getFloor(int floorNumber){
        return allFloors.get( floorNumber - 1 );
    }

//    returns a string representing the state of the building, and all its floors and all its elevators
    public String toString(){
        StringBuilder visualRepresentation = new StringBuilder();
        for (int i = allFloors.size(); i > 0; i--){

//            adds a padding so the floor numbers line up visually
            if (i < 10){
                visualRepresentation.append("  ").append(i).append(":  |");
            }
            else if(i < 100){
                visualRepresentation.append(" ").append(i).append(":  |");
            }
            else{
                visualRepresentation.append("").append(i).append(":  |");
            }

            for(int j = 0; j < elevatorArrayList.size(); j++){
                if(elevatorArrayList.get(j).getmCurrentFloor() == i){
                    visualRepresentation.append(" X |");
                }
                else{
                    visualRepresentation.append("   |");
                }
            }

            visualRepresentation.append(" ");

//            displays the passengers waiting on the building floor
            for(int k = 0; k < getFloor(i).size(); k++){
                visualRepresentation.append(" ").append(getFloor(i).get(k));
            }

            visualRepresentation.append("\n");
        }

        for (int i = 0; i < numElevators; i++){
            visualRepresentation.append(elevatorArrayList.get(i)).append("\n");
        }
        return visualRepresentation.toString();
    }

//  updates the state of the building by spawning in passengers
    public void tick(){
        int randNum;
//        goes through all floors to try to spawn a passenger
//        i = current floor
        for(int i = 0; i < allFloors.size(); i++){
//        spawns a passenger if the random number generated == 0
            randNum = Simulation.mRandom.nextInt(20);
            if (randNum == 0) {
//            continuously loops until a passenger spawns on a floor that does not contain an elevator
                while(randNum == 0 || randNum == i + 1) {
//                    randNum = destination floor
                    randNum = Simulation.mRandom.nextInt(numFloors) + 1;
                }
//                if true, spawns a person that is not on the same floor as its destination
                System.out.println("Adding person with destination " + randNum + " to floor " + (i + 1));
                allFloors.get(i).add(randNum);

                break;
            }
        }

//        updates all elevators.
        for(Elevator i: elevatorArrayList){
            i.tick();
        }

//        prints the buildings layout
        System.out.println(toString());
    }
}
