import java.util.ArrayList;

public class Building {
//  private ArrayList<Integer> floor = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> allFloors = new ArrayList<>();
    private ArrayList<Elevator> elevatorArrayList = new ArrayList<>();
    private int numFloors, numElevators;

    // constructs a building with the user inputting the amount of floors and elevators in the building.
    public Building(int numFloors, int numElevators){
        String building = "Kyle Corp.";
        this.numFloors = numFloors;
        this.numElevators = numElevators;
        for (int i = 0; i < numFloors; i++){
            this.allFloors.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < numElevators; i++){
            this.elevatorArrayList.add(new Elevator(i + 1 , building));
        }
    }

//  returns the array list of a floors passengers
    public ArrayList<Integer> getFloor(int floorNumber){
        return allFloors.get( floorNumber - 1 );
    }

//  returns a string representing the state of the building, and all its floors and all its elevators
    public String toString(){
        String visualRepresentation = "";
        for (int i = 0; i < numElevators; i++){
            visualRepresentation = visualRepresentation + elevatorArrayList.get(i) + "\n";
        }
        return visualRepresentation;
    }

//  updates the state of the building by spawning in passengers
    public void tick(){
        int randNum;
        randNum = Simulation.mRandom.nextInt(20);
//        spawns a passenger if the random number generated == 0
        if (randNum == 0){
//            continuously loops until a passenger spawns on a floor that does not contain an elevator
            while(true){
                randNum = Simulation.mRandom.nextInt(numFloors + 1);
                if (randNum != elevatorArrayList.get(0).getmCurrentFloor()){
                    System.out.println("Adding person with destination " + randNum + " to floor "
                            + elevatorArrayList.get(0).getmCurrentFloor());
                    allFloors.get(0).add(randNum);
                    break;
                }
            }
        }
        for(int i = 0; i < elevatorArrayList.size(); i++){
            elevatorArrayList.get(i).tick();
        }
        System.out.println(toString());
    }
}
