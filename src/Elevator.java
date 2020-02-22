import java.util.*;

public class Elevator {
    private int mCurrentFloor = 1;
    private int mDestinationFloor;
    private int mElevatorNum;
    private HashMap<Integer, Integer> passengers = new HashMap<>();
    private currentState mCurrentState;
    private currentDirection mCurrentDirection;
    private Building mReference;

    public Elevator(int elevatorNum, Building reference){
        mCurrentState = currentState.IDLE_STATE;
        mCurrentDirection = currentDirection.NOT_MOVING;
        mReference = reference;
        mElevatorNum = elevatorNum;
    }

    public int getmCurrentFloor() {
        return mCurrentFloor;
    }

    public int getmElevatorNum(){
        return mElevatorNum;
    }

    public Map getPassengers(){
        return passengers;
    }

    enum currentDirection {
        UP,
        DOWN,
        NOT_MOVING
    }

    enum currentState{
        IDLE_STATE,
        DOORS_OPENING,
        UNLOADING_PASSENGERS,
        LOADING_PASSENGERS,
        DOORS_CLOSING,
        ACCELERATING,
        MOVING,
        DECELERATING
    }

    // updates the current state of the elevator
    public void tick(){
        ArrayList<Integer> buildingFloor = mReference.getFloor(mCurrentFloor);
        switch (mCurrentState){
            case IDLE_STATE:
                if(passengers.size() > 0){
                    mCurrentState = currentState.ACCELERATING;
                }
                else if(mReference.getFloor(mCurrentFloor).size() > 0){
                    mCurrentState = currentState.DOORS_OPENING;
                }
                break;

            case DOORS_OPENING:
                if (passengers.containsKey(mCurrentFloor)){
                    mCurrentState = currentState.UNLOADING_PASSENGERS;
                }
                else{
                    mCurrentState = currentState.LOADING_PASSENGERS;
                }

                break;

            case UNLOADING_PASSENGERS:
//                removes passengers equal to the current floor
                passengers.remove(mCurrentFloor);

                if(passengers.size() == 0){
                    mCurrentDirection = currentDirection.NOT_MOVING;
                }
                if (mCurrentDirection == currentDirection.NOT_MOVING ||
                        (floorPassengersUp() && mCurrentDirection == currentDirection.UP) ||
                        (floorPassengersDown() && mCurrentDirection == currentDirection.DOWN)){
                    mCurrentState = currentState.LOADING_PASSENGERS;
                }
                else{
                    mCurrentState = currentState.DOORS_CLOSING;
                }
                break;

            case LOADING_PASSENGERS:
//                if the current direction is not moving and the building floor contains waiting passengers
                if(mCurrentDirection == currentDirection.NOT_MOVING && buildingFloor.size() != 0){
                    passengers.put(buildingFloor.get(0), 1);
//                    if the first passenger is heading up set the destination floor and direction
                    if(buildingFloor.get(0) > mCurrentFloor){
                        mCurrentDirection = currentDirection.UP;
                        mDestinationFloor = buildingFloor.get(0);
                        buildingFloor.remove(0);
                    }
//                    if the fist passenger is heading down set the destination floor and direction
                    else if(buildingFloor.get(0) < mCurrentFloor){
                        mCurrentDirection = currentDirection.DOWN;
                        mDestinationFloor = buildingFloor.get(0);
                        buildingFloor.remove(0);
                    }
                }
//                checks if the current directions is up, if so adds passengers going up to the elevator
//                removes passenger from building floor
                if(mCurrentDirection == currentDirection.UP && buildingFloor.size() != 0){
                    int buildingIndex = 0;
                    for (int person: buildingFloor) {
//                        checks if the passenger already exists in the map
                        if( passengers.containsKey(person) && person > mCurrentFloor){
                            passengers.replace(person, passengers.get(person) + 1);
                            buildingFloor.remove(buildingIndex);
                        }
//                        adds  not yet existing passenger to the map removes passenger from building floor
                        else if (person > mCurrentFloor){
                            passengers.put(person, 1);
                            buildingFloor.remove(buildingIndex);
                        }
                        buildingIndex++;
                        if (buildingFloor.size() == 0){
                            break;
                        }
                    }
                }

//                checks if the current directions is down, if so adds passengers going down to the elevator
//                removes passenger from building floor
                if(mCurrentDirection == currentDirection.DOWN && buildingFloor.size() != 0){
                    int buildingIndex = 0;
                    for (int person: buildingFloor) {
//                        checks if the passenger already exists in the map if so value++
                        if( passengers.containsKey(person) && person < mCurrentFloor){
                            passengers.replace(person, passengers.get(person) + 1);
                            buildingFloor.remove(buildingIndex);
                        }
//                        adds not yet existing passenger to the map removes passenger from building floor
                        else if (person < mCurrentFloor){
                            passengers.put(person, 1);
                            buildingFloor.remove(buildingIndex);
                        }
                        buildingIndex++;
                        if (buildingFloor.size() == 0){
                            break;
                        }
                    }
                }

                mCurrentState = currentState.DOORS_CLOSING;
                break;

            case DOORS_CLOSING:
                if(passengers.size() > 0){
                    mCurrentState = currentState.ACCELERATING;
                }
                else{
                    mCurrentState = currentState.IDLE_STATE;
                }
                break;

            case ACCELERATING:
                mCurrentState = currentState.MOVING;

            case MOVING:
                if (mCurrentDirection == currentDirection.UP){
                    mCurrentFloor += 1;
                }
                else {
                    mCurrentFloor -= 1;
                }

                if ( passengers.containsKey(mCurrentFloor)){
                    mCurrentState = currentState.DECELERATING;
                }

                break;

            case DECELERATING:
                mCurrentState = currentState.DOORS_OPENING;
                if (passengers.size() == 0){
                    mCurrentDirection = currentDirection.NOT_MOVING;
                }
                break;

            default:
                break;
        }
    }

//    returns true if passengers on the current floor are going up
    private Boolean floorPassengersUp() {
        for (Integer floorPassenger : mReference.getFloor(mCurrentFloor)) {
            if (floorPassenger > mCurrentFloor) {
                return true;
            }
        }
        return false;
    }

//    returns true if passengers on the current floor are going down
    private Boolean floorPassengersDown(){
        for (Integer floorPassenger: mReference.getFloor(mCurrentFloor)){
            if (floorPassenger < mCurrentFloor){
                return true;
            }
        }
        return false;
    }

    // represents the overall state of the elevator
    public String toString(){
        return "Elevator " + mElevatorNum + " - Floor " + mCurrentFloor + " - " + mCurrentState + " - " +
                mCurrentDirection + " - Passengers " + passengers.keySet().toString();
    }
}
