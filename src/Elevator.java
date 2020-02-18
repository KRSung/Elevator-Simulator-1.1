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
//    TODO: finish the switch cases
    public void tick(){
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

//            FiXME: finish the Loading passengers stage
            case LOADING_PASSENGERS:
                if(mCurrentDirection == currentDirection.UP){
                    for (int i: mReference.getFloor(mCurrentFloor)) {
                        if( passengers.containsKey(i) ){
                            passengers.replace(i, passengers.get(i) + 1);
                        }
                        else{
                            passengers.put(i, 1);
                        }
                    }
                }

            case DECELERATING:
                mCurrentState = currentState.DOORS_OPENING;
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
                mCurrentDirection + " - Passengers " + passengers;
    }
}
