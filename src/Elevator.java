import java.util.*;

public class Elevator {
    private int mCurrentFloor = 1;
    private int mElevatorNum;
    private ArrayList<Integer> passengers = new ArrayList<>();
    private currentState mCurrentState, mPreviousState;
    private currentDirection mCurrentDirection;
    private Building mReference;

    public Elevator(int elevatorNum, Building reference){
        mCurrentState  = mPreviousState = currentState.IDLE_STATE;
        mCurrentDirection = currentDirection.NOT_MOVING;
        mReference = reference;
        mElevatorNum = elevatorNum;
    }

    public int getmCurrentFloor() {
        return mCurrentFloor;
    }

    enum currentDirection {
        MOVING_UP,
        MOVING_DOWN,
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
                if (passengers.contains(mCurrentFloor)){
                    mCurrentState = currentState.UNLOADING_PASSENGERS;
                }
                else{
                    mCurrentState = currentState.LOADING_PASSENGERS;
                }

                break;

            case UNLOADING_PASSENGERS:
//                removes passengers equal to the current floor
                for(int i = 0; i < passengers.size(); i++){
                    if(passengers.get(i) == mCurrentFloor){
                        passengers.remove(i);
                        i--;
                    }
                }

                if(passengers.size() == 0){
                    mCurrentDirection = currentDirection.NOT_MOVING;
                }
                if ((mCurrentDirection == currentDirection.NOT_MOVING ||
                        (floorPassengersUp() && mCurrentDirection == currentDirection.MOVING_UP) ||
                        (floorPassengersDown() && mCurrentDirection == currentDirection.MOVING_DOWN))
                        && buildingFloor.size() != 0){
                    mCurrentState = currentState.LOADING_PASSENGERS;
                }
                else{
                    mCurrentState = currentState.DOORS_CLOSING;
                }
                break;

            case LOADING_PASSENGERS:
//                if the current direction is not moving and the building floor contains waiting passengers
                if(mCurrentDirection == currentDirection.NOT_MOVING && buildingFloor.size() != 0){
                    passengers.add(buildingFloor.get(0));
//                    if the first passenger is heading up set the destination floor and direction
                    if(buildingFloor.get(0) > mCurrentFloor){
                        mCurrentDirection = currentDirection.MOVING_UP;
                        buildingFloor.remove(0);
                    }
//                    if the fist passenger is heading down set the destination floor and direction
                    else if(buildingFloor.get(0) < mCurrentFloor){
                        mCurrentDirection = currentDirection.MOVING_DOWN;
                        buildingFloor.remove(0);
                    }
                }
//                checks if the current directions is up, if so adds passengers going up to the elevator
//                removes passenger from building floor
                if(mCurrentDirection == currentDirection.MOVING_UP && buildingFloor.size() != 0){
                    for(int person: buildingFloor){
                        if(person > mCurrentFloor){
                            passengers.add(person);
                        }
                    }
                    for (int i = buildingFloor.size() - 1; i >= 0; i--) {
//                        checks if the passenger already exists in the map if so value++
                        if( passengers.contains(buildingFloor.get(i)) && buildingFloor.get(i) > mCurrentFloor){
//                            passengers.replace(buildingFloor.get(i), passengers.get(buildingFloor.get(i)) + 1);
                            buildingFloor.remove(buildingFloor.get(i));
                        }
//                        adds not yet existing passenger to the map removes passenger from building floor
                        else if (buildingFloor.get(i) > mCurrentFloor){
//                            passengers.put(buildingFloor.get(i), 1);
                            buildingFloor.remove(buildingFloor.get(i));
                        }
                        if (buildingFloor.size() == 0){
                            break;
                        }
                    }
                }

                if(mCurrentDirection == currentDirection.MOVING_DOWN && buildingFloor.size() != 0){
                    for(int person: buildingFloor){
                        if(person < mCurrentFloor){
                            passengers.add(person);
                        }
                    }

                    for (int i = buildingFloor.size() - 1; i >= 0; i--) {
//                        checks if the passenger already exists in the map if so value++
                        if( passengers.contains(buildingFloor.get(i)) && buildingFloor.get(i) < mCurrentFloor){
//                            passengers.replace(buildingFloor.get(i), passengers.get(buildingFloor.get(i)) + 1);
                            buildingFloor.remove(buildingFloor.get(i));
                        }
//                        adds not yet existing passenger to the map removes passenger from building floor
                        else if (buildingFloor.get(i) < mCurrentFloor){
//                            passengers.put(buildingFloor.get(i), 1);
                            buildingFloor.remove(buildingFloor.get(i));
                        }
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
                mPreviousState = currentState.MOVING;
                break;

            case MOVING:
                if(mPreviousState == currentState.MOVING){
                    if (mCurrentDirection == currentDirection.MOVING_UP){
                        mCurrentFloor += 1;
                    }
                    else {
                        mCurrentFloor -= 1;
                    }
                }

//                Stops if there is a passenger arriving on the current floor
                if ( passengers.contains(mCurrentFloor) ||
                        (floorPassengersDown() && mCurrentDirection == currentDirection.MOVING_DOWN) ||
                        (floorPassengersUp() && mCurrentDirection == currentDirection.MOVING_UP)){
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
                mCurrentDirection + " - Passengers " + passengers.toString();
    }
}
