import java.util.ArrayList;

public class Building {
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
            this.elevatorArrayList.add(new Elevator(i + 1 , this));
        }
    }

//    returns the array list of a floors passengers
    public ArrayList<Integer> getFloor(int floorNumber){
        return allFloors.get( floorNumber - 1 );
    }

//    returns a string representing the state of the building, and all its floors and all its elevators
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
        for(int i = 0; i < allFloors.size(); i++){
//        spawns a passenger if the random number generated == 0
            randNum = Simulation.mRandom.nextInt(20);
            if (randNum == 0) {
//            continuously loops until a passenger spawns on a floor that does not contain an elevator
                while(true){
                    randNum = Simulation.mRandom.nextInt(numFloors + 1);
//                    if true, spawns a person
                    if (randNum != i){
                        System.out.println("Adding person with destination " + randNum + " to floor "
                                + (i + 1));
                        allFloors.get(i).add(randNum);
                        break;
                    }
                }
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
