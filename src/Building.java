import java.util.ArrayList;

public class Building {
    //    private ArrayList<Integer> floor = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> floors = new ArrayList<>();
    private int numFloors, numElevators;


    public Building(int numFloors, int numElevators){
        this.numFloors = numFloors;
        this.numElevators = numElevators;
    }

    public ArrayList<Integer> getFloor(int floorNumber){
        return floors.get( floorNumber - 1 );
    }

    public String toString(){
        return "";
    }

    public void tick(){
        int randNum;
        randNum = Simulation.mRandom.nextInt(20);
        if (randNum == 0){
            randNum = Simulation.mRandom.nextInt(numFloors + 1);
//            if (randNum != .getcurrentFloor()){
//                System.out.println("Adding person with destination 3 to floor 1");
//            }
        }
    }
}
